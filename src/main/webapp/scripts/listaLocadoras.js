document.addEventListener('DOMContentLoaded', function () {
    console.log("PASSEI POR BRUH1")
    loadCidades();

    var selectCidade = document.getElementById('selectCidade');

    selectCidade.addEventListener('change', function () {
        var cidadeSelecionada = selectCidade.value;
        var xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var locadorasTableContainer = document.getElementById('locadorasTableContainer');
                locadorasTableContainer.innerHTML = xhr.responseText;
            }
        };

        try {
        xhr.open('GET', '../filtrar_locadoras.jsp?cidadeSelecionada=' + cidadeSelecionada, true);
        xhr.send();
        }
        catch (error) {
            console.log("rbuh5000")
        }
    });
});