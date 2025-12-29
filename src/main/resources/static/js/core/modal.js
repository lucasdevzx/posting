document.addEventListener('DOMContentLoaded', function () {
  const modalLogin = document.getElementById('modal-login');
  const buttonLogin = document.getElementById('button-login');
  const modalRegister = document.getElementById('modal-register');
  const buttonRegister = document.getElementById('button-register');

  buttonRegister.addEventListener('click', () => {
    modalRegister.classList.add('modal-overlay-active');
    modalLogin.classList.remove('modal-overlay-active');
    modalLogin.classList.add('modal-overlay');
    //buttonRegister.style.display = "none";
    buttonLogin.style.display = "inline-flex";
  });

  buttonLogin.addEventListener('click', () => {
    modalRegister.classList.remove('modal-overlay-active');
    modalLogin.classList.remove('modal-overlay');
    modalLogin.classList.add('modal-overlay-active');
    //buttonLogin.style.display = "none";
    buttonRegister.style.display = "inline-flex";
  })



})
