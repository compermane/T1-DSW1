package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.ErrorResponse;

@RestController
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

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

    private void parse(Cliente cliente, JSONObject json) {
        Object id = json.get("id");

        if (id != null) {
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            }
            else {
                cliente.setId((Long) id);
            }
        }

        cliente.setCpf((String) json.get("cpf"));
        cliente.setNome((String) json.get("nome"));
        cliente.setIsAdmin(false);
        cliente.setRole("ROLE_CLIENTE");
        cliente.setSexo((String) json.get("sexo"));

        cliente.setTelefone((String) json.get("telefone"));
        cliente.setUsername((String) json.get("email"));
        
        String senha = (String) json.get("senha");
        cliente.setPassword(encoder.encode(senha));

        String dataNascimentoStr = (String) json.get("data_nascimento");
        if (dataNascimentoStr != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = dateFormat.parse(dataNascimentoStr);
                
                Date sqlDate = new Date(utilDate.getTime());
                cliente.setDataNascimento(sqlDate);
            } 
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // Lista todos os clientes
    @GetMapping(path = "/clientes")
    private ResponseEntity<List<Cliente>> lista() {
        List<Cliente> listaClientes = clienteService.buscarTodos();

        if (listaClientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listaClientes);
    }

    // Lista usuario por id
    @GetMapping(path = "/clientes/{id}")
    private ResponseEntity<?> lista(@PathVariable("id") Long id) {
        try {
            Cliente cliente = clienteService.buscarPorID(id);

            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(cliente);
        }
        catch (Exception e) {
            ErrorResponse errorResponse;
            HttpStatus httpStatus;

            if (e.getMessage().contains("não encontrado")) {
                errorResponse = new ErrorResponse("Erro ao listar", HttpStatus.UNPROCESSABLE_ENTITY, "Cliente de id " + id + " não encontrado: " + e.getMessage());
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else {
                errorResponse = new ErrorResponse("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    }

    // Cadastra cliente 
    @PostMapping(path = "/clientes")
    @ResponseBody
    public ResponseEntity<?> cadastrar(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = new Cliente();
                parse(cliente, json);

                clienteService.salvar(cliente);
                return ResponseEntity.ok(cliente);
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
                errorResponse = new ErrorResponse("Erro no procesamento", HttpStatus.UNPROCESSABLE_ENTITY, "Email ou CPF já cadastrados: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
    }

    // Atualiza um cliente
    @PutMapping(path = "/clientes/{id}")
    public ResponseEntity<?> atualiza(@PathVariable("id") Long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Cliente cliente = clienteService.buscarPorID(id);
                if (cliente == null) {
                    return ResponseEntity.notFound().build();
                }
                else {
                    parse(cliente, json);
                    clienteService.salvar(cliente);

                    return ResponseEntity.ok(cliente);
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
                errorResponse = new ErrorResponse("Erro ao atualizar", HttpStatus.UNPROCESSABLE_ENTITY, "Cliente de id " + id + " não encontrada: " + e.getMessage());
            else {
                errorResponse = new ErrorResponse("Erro ao atualizar", HttpStatus.UNPROCESSABLE_ENTITY, "Campos inválidos: " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
    }

    // Deleta um cliente por id
    @DeleteMapping(path = "/clientes/{id}")
    public ResponseEntity<?> deleta(@PathVariable("id") Long id) {
        try {
            Cliente cliente = clienteService.buscarPorID(id);

            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }
            
            clienteService.excluir(id);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } 
        catch (Exception e) {
            ErrorResponse errorResponse;
            HttpStatus httpStatus;
            if (e.getMessage().contains("não encontrado")) {
                errorResponse = new ErrorResponse("Erro ao deletar", HttpStatus.UNPROCESSABLE_ENTITY, "Cliente de id " + id + " não encontrado: " + e.getMessage());
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