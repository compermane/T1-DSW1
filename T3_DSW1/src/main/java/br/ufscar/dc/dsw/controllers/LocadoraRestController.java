package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import br.ufscar.dc.dsw.domain.ErrorResponse;
import br.ufscar.dc.dsw.domain.Locadora;

@RestController
public class LocadoraRestController {

    @Autowired
    private ILocadoraService locadoraService;

	@Autowired
	private BCryptPasswordEncoder encoder;

    private Boolean isJSONValid(String jsonString) {
        try {
            return new ObjectMapper().readTree(jsonString) != null;
        }
        catch (IOException e) {
            return false;
        }
    }

    private void parse(Locadora locadora, JSONObject json) {
        Object id = json.get("id");

        if (id != null) {
            if (id instanceof Integer) {
                locadora.setId(((Integer) id).longValue());
            }
            else {
                locadora.setId((Long) id);
            }
        }

        locadora.setCnpj((String) json.get("cnpj"));
        locadora.setNome((String) json.get("nome"));
        locadora.setCidade((String) json.get("cidade"));
        locadora.setIsAdmin(false);

        locadora.setRole("ROLE_LOCADORA");

        locadora.setUsername((String) json.get("email"));
        
        String senha = (String) json.get("senha");
        locadora.setPassword(encoder.encode(senha));

    }

    // Lista todos os locadoras
    @GetMapping(path = "/locadoras")
    private ResponseEntity<List<Locadora>> lista() {
        List<Locadora> listaLocadoras = locadoraService.buscarTodos();

        if (listaLocadoras.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaLocadoras);
    }

    // Lista locadora por cidade
    @GetMapping(path = "/locadoras/cidades/{nome}")
    private ResponseEntity<?> lista(@PathVariable("nome") String nome) {
        try {
            List<Locadora> locadoras = locadoraService.buscarPorCidade(nome);

            if (locadoras == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(locadoras);
        }
        catch(Exception e) {
            ErrorResponse errorResponse;
            HttpStatus httpStatus;

            if (e.getMessage().contains("não encontrada")) {
                errorResponse = new ErrorResponse("Erro ao listar", HttpStatus.UNPROCESSABLE_ENTITY, "Locadoras de cidade " + nome + " não encontrada: " + e.getMessage());
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else {
                errorResponse = new ErrorResponse("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    }
    // Lista locadora por id
    @GetMapping(path = "/locadoras/{id}")
    private ResponseEntity<?> lista(@PathVariable("id") Long id) {
        try {
            Locadora locadora = locadoraService.buscarPorID(id);

            if (locadora == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(locadora);
        }
        catch(Exception e) {
            ErrorResponse errorResponse;
            HttpStatus httpStatus;

            if (e.getMessage().contains("não encontrada")) {
                errorResponse = new ErrorResponse("Erro ao listar", HttpStatus.UNPROCESSABLE_ENTITY, "Locadora de id " + id + " não encontrada: " + e.getMessage());
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else {
                errorResponse = new ErrorResponse("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    }

    // Cadastra locadora 
    @PostMapping(path = "/locadoras")
    @ResponseBody
    public ResponseEntity<?> cadastrar(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Locadora locadora = new Locadora();
                parse(locadora, json);

                locadoraService.salvar(locadora);
                return ResponseEntity.ok(locadora);
            }
            else {
                return ResponseEntity.badRequest().body(null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse;
            if (e.getMessage().contains("cannot be null")) 
                errorResponse = new ErrorResponse("Erro no processamento", HttpStatus.UNPROCESSABLE_ENTITY, "Campos inválidos");
            else 
                errorResponse = new ErrorResponse("Erro no procesamento", HttpStatus.UNPROCESSABLE_ENTITY, "Email ou CNPJ já cadastrados: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
    }

    // Atualiza um locadora
    @PutMapping(path = "/locadoras/{id}")
    public ResponseEntity<?> atualiza(@PathVariable("id") Long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Locadora locadora = locadoraService.buscarPorID(id);
                if (locadora == null) {
                    return ResponseEntity.notFound().build();
                }
                else {
                    parse(locadora, json);
                    locadoraService.salvar(locadora);

                    return ResponseEntity.ok(locadora);
                }
            }
            else {
                return ResponseEntity.badRequest().body(null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse;

            if (e.getMessage().contains("não encontrada")) 
                errorResponse = new ErrorResponse("Erro ao atualizar", HttpStatus.UNPROCESSABLE_ENTITY, "Locadora de id " + id + " não encontrada: " + e.getMessage());
            else {
                errorResponse = new ErrorResponse("Erro ao atualizaar", HttpStatus.UNPROCESSABLE_ENTITY, "Campos inválidos: " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
    }

    // Deleta um locadora por id
    @DeleteMapping(path = "/locadoras/{id}")
    public ResponseEntity<?> deleta(@PathVariable("id") Long id) {
        try {
            Locadora locadora = locadoraService.buscarPorID(id);

            if (locadora == null) {
                System.err.println("BRUH");
                return ResponseEntity.notFound().build();
            }
            
            locadoraService.excluir(id);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        catch(Exception e) {
            ErrorResponse errorResponse;
            HttpStatus httpStatus;
            if (e.getMessage().contains("não encontrada")) {
                errorResponse = new ErrorResponse("Erro ao deletar", HttpStatus.UNPROCESSABLE_ENTITY, "Locadora de id " + id + " não encontrada: " + e.getMessage());
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else {
                errorResponse = new ErrorResponse("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    }
}