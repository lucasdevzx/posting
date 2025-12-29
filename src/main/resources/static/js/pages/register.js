document.addEventListener('DOMContentLoaded', function () {

  // Capturar o formulario pelo ID
  const form = document.getElementById('form-register');

  // Capturar os campos pelo input
  const inputName = document.getElementById('register-name');
  const inputEmail = document.getElementById('register-email');
  const inputPassword = document.getElementById('register-password');

  // Capturar botão e div de mensagem(exception)
  const btnSubmit = document.getElementById('register-submit');
  const message = document.getElementById('register-message');

  console.log('Elementos Capturados', form, inputName, inputEmail);

  // Adicionar listener no formulário
  form.addEventListener('submit', function (evento) {
    // 'evento' é um objeto que contém informações sobre o submit

    // IMPORTANTE: Previnir o comportamento de reload do form HTML
    evento.preventDefault();
    console.log('Formulário enviado!');

    // Chamar função que vai processar o registro
    processarRegistro();
  });

  // Coletar dados dos inputs
  function coletarDados() {

    // Criar um objeto JavaScript
    const user = {
      name: inputName.value.trim(),
      email: inputEmail.value.trim(),
      password: inputPassword.value
    }

    return user;
  }

  function mostrarMensagem(texto, tipo) {
    // Tipo pode ser: 'sucesso' ou 'erro'

    // Definir o texto da mensagem
    message.textContent = texto;

    // Definir as classes CSS (remove antigas e adiciona novas)
    message.className = 'mensagem ' + tipo;

    // Tornar a div visivel
    message.style.display = 'block';

    // Esconder automaticamente após 5 segundos
    setTimeout(function () {
      message.style.display = 'none';
    }, 5000);
  }

  function processarRegistro() {
    console.log('Iniciando processo de registro...');

    // Passo 1: Coletar os dados
    const user = coletarDados();

    // Passo 2: Desabilitar o botão para evitar múltiplos cliques
    btnSubmit.disabled = true;
    btnSubmit.value = 'Cadastrando...';

    // Passo 3: Enviar para a API
    enviarParaAPI(user);
  }

  async function enviarParaAPI(user) {

    const url = '/auth/register';

    try {
      // await "espera" a Promise resolver (similar a um bloqueio)
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user) // Transforma objeto JS em JSON
      });

      console.log('Resposta recebida!', response);
      console.log('Status:' + response.status);

      // Verificar se deu erro
      if (!response.ok) {
        throw new Error('Erro HTTP:' + response.status);
      }

      // Parsear JSON
      const data = await response.json();
      console.log('Dados da API', data);

      // Sucesso!
      //mostrarMensagem('Cadastro realizado com sucesso!', 'sucesso');
      form.reset();

      // Redirecionar
      setTimeout(() => {
        window.location.href = '/auth.html';
      }, 2000);

    } catch (error) {
      console.error('Erro', error);
      mostrarMensagem('Erro ao realizar o cadastro. Tente novamente.', 'erro');
    } finally {
      btnSubmit.disabled = false;
      btnSubmit.value = 'Cadastrar';
    }
  }

});
