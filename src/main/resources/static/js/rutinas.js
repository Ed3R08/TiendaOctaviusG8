//Esta funcion carga una imagen local en la pagina
function readURL(input) {
    if (input.files && input.files[0]) {
        var lector = new FileReader();

        lector.onload = function (e) {
            $("#blah").attr("src", e.target.result)
                    .height(200);

        };
        lector.readAsDataURL(input.files[0]);
    }

}

//Esta funcion se ejecuta "dentro del browser "localmente y lo q realiza es 
//incluir un elemento en el carrito de compras... si hat existencias...

function addCart(formulario) {
    var idProducto = formulario.elements[0].value;
    var existencias = formulario.elements[1].value;
    if (existencias > 0) {
        var ruta = "/carrito/agregar/" + idProducto;
        $("#resultBlock").load(ruta);

    } else {
        window.alert("No hay existencias del producto");
    }

}