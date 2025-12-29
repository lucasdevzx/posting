async function processarResposta(response) {
    console.log('Status:', response.status);
    console.log('Response OK:', response.ok);

    // Ler corpo da resposta (uma única vez)
    let dados;
    const contentType = response.headers.get('content-type');

    if (contentType && contentType.includes('application/json')) {
        dados = await response.json();
    } else {
        const texto = await response.text();
        dados = { message: texto };
    }

    console.log('Dados recebidos:', dados);

    // Se deu erro, extrair mensagem e lançar
    if (!response.ok) {
        const mensagemErro = extrairMensagem(dados);
        const erroPersonalizado = personalizarErroPorStatus(response.status, mensagemErro);
        throw new Error(erroPersonalizado);
    }

    // Se deu certo, retorna os dados
    return dados;
}

function extrairMensagem(dados) {
    // Formato 1: { message: "..." }
    if (dados.message) {
        return dados.message;
    }

    // Formato 2: { error: "..." }
    if (dados.error) {
        return dados.error;
    }

    // Formato 3: { errors: ["erro1", "erro2"] }
    if (dados.errors && Array.isArray(dados.errors)) {
        return dados.errors.join(', ');
    }

    // Formato 4: { errors: { campo: "mensagem" } }
    if (dados.errors && typeof dados.errors === 'object') {
        const mensagens = Object.values(dados.errors);
        return mensagens.join(', ');
    }

    // Se não encontrou nenhum formato, retornar genérico
    return 'Erro desconhecido';
}

function personalizarErroPorStatus(status, mensagemOriginal) {
    switch (status) {
        case 400:
            return 'Dados inválidos: ' + mensagemOriginal;

        case 401:
            return 'Email ou senha incorretos';

        case 403:
            return 'Acesso negado';

        case 404:
            return 'Recurso não encontrado';

        case 409:
            return 'Conflito: ' + mensagemOriginal;

        case 500:
            return 'Erro no servidor. Tente novamente mais tarde.';

        case 503:
            return 'Serviço temporariamente indisponível';

        default:
            return mensagemOriginal || 'Erro ao processar requisição';
    }
}

console.log('error-handler.js carregado com sucesso!');