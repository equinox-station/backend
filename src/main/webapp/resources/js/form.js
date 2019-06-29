function copy(id) {
	document.getElementById('modform:id').value = id;
	document.getElementById('modform:login').value = document.getElementById('login' + id).innerHTML;
	document.getElementById('modform:nome').value = document.getElementById('nome' + id).innerHTML;
	document.getElementById('modform:cargo').value = document.getElementById('cargo' + id).innerHTML;
	document.getElementById('modform:telefone').value = document.getElementById('telefone' + id).innerHTML;
	
	modificarAcao(1);
}

function clear() {
	document.getElementById('modform:id').value = 0;
	document.getElementById('modform:login').value = "";
	document.getElementById('modform:nome').value = "";
	document.getElementById('modform:cargo').value = "";
	document.getElementById('modform:telefone').value = "";
}

function startnew() {
	clear()
	
	modificarAcao(0);
}

function modificarAcao(tipo) {
	if (tipo == 0) document.getElementById('tipoAcao').innerHTML = "Insert";
	else document.getElementById('tipoAcao').innerHTML = "Update";
}
