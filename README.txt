=======================================================================
ROTEIRO DE EXECUÇÃO - SISTEMA DE GERENCIAMENTO ACADÊMICO
=======================================================================
Projeto focado na integração e comunicação entre cliente/servidor.

-----------------------------------------------------------------------
1. TECNOLOGIAS UTILIZADAS
-----------------------------------------------------------------------
Este projeto foi desenvolvido utilizando a seguinte stack de tecnologias:
- Frontend: Java (Swing)
- Backend: PHP (API REST)
- Banco de Dados: MySQL

-----------------------------------------------------------------------
2. CONFIGURAÇÃO DO BANCO DE DADOS
-----------------------------------------------------------------------
Antes de rodar a aplicação, é necessário preparar o banco de dados.
O script SQL contendo a criação do esquema e das tabelas está 
localizado no seguinte caminho:

Caminho: backend/src/data/script_esquema.sql

-> Execute este script no seu SGBD preferido e garanta que todas as tabelas foram criadas.

Após isso, de forma opcional, há um segundo script que insere alguns dados no esquema, para que se tenha
informações para se manipular ao executar a aplicação. 

-> O script de carga de dados está no seguinte caminho: backend/src/data/inserts_iniciais.sql 

-----------------------------------------------------------------------
3. CREDENCIAIS DO BANCO DE DADOS
-----------------------------------------------------------------------
Para que a API PHP consiga se comunicar com o MySQL, configure 
as suas credenciais locais (usuário, senha e nome do banco).

As credenciais devem ser alteradas no arquivo de conexão:

Caminho: backend/src/data/Database.php

-----------------------------------------------------------------------
4. CONFIGURAÇÃO DO SERVIDOR WEB (BACKEND)
-----------------------------------------------------------------------
A API em PHP precisa ser interpretada por um servidor Apache (como 
o XAMPP, WAMP ou similar).

-> Copie ou mova a pasta "backend" para dentro do diretório público 
de roteamento do seu servidor (ex: pasta "htdocs" no XAMPP ou "www").

O sistema frontend está programado para buscar as requisições na 
seguinte estrutura de URL:
http://localhost/backend/recurso_aqui.php

-----------------------------------------------------------------------
5. EXECUÇÃO DO APLICATIVO (FRONTEND)
-----------------------------------------------------------------------
O aplicativo cliente (interface gráfica) já está compilado e pronto 
para ser executado.

Para abrir o sistema, abra o terminal (ou prompt de comando) na 
pasta raiz onde o executável se encontra e rode o seguinte comando:

Comando: java -jar gerenciamento_academico.jar

=======================================================================
