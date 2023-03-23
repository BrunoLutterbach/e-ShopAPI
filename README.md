# <h1 align="center"> Projeto E-commerce com Java e Spring Boot </h1>
<p align="center">
<img src="http://img.shields.io/static/v1?label=STATUS&message=%20FINALIZADO&color=black&style=for-the-badge"/>
</p>

<p align="center">
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white">
</p>
<hr>

#### Projeto
Este projeto é uma aplicação de e-commerce desenvolvida com Java e Spring Boot, permitindo aos usuários realizar compras de produtos. O sistema oferece as funcionalidades de autenticação e gerenciamento de usuários, produtos, pedidos e endereços.

#
#### Funcionalidades

-  CRUD de usuário: Cadastro, consulta, atualização e exclusão de usuários.

- CRUD de endereço: Cadastro, consulta, atualização e exclusão de endereços.

- CRUD de produto: Cadastro, consulta, atualização e exclusão de produtos.

- CRUD de pedido: Cadastro, consulta e exclusão de pedidos.

- Carrinho de compras por sessão: Adicionar, remover produtos e visualizar o carrinho da sessão do usuário.

- Autenticação: Sistema de autenticação para acesso a api.

- Integração com PayPal: Realização de pagamentos utilizando a plataforma PayPal.

```mermaid
graph TD;
A[Cadastrar] --> B(Autenticar);
B --> C[Consultar produtos];
C --> D[Adicionar produtos ao carrinho];
D --> E(Criar pedido);
E --> F(Gerar link de pagamento);
F --> G(Realizar pagamento no PayPal);
G --> H(Visualizar detalhes do pedido);
```
----

#### URL Deploy
> 

#

### Rotas
#
### Autenticação
| Método      | Rota        | Descrição | JSON |
| ----------- | ----------- | ---------- | ----------  |
| POST         | /api/auth       | Retornar o Bearer token <br> necessário nas requisições |  <pre>{<br>"email": "joao.silva@email.com",<br>"senha": "senha123"<br>}</pre>|

#

### 1 Usuário
#### 1.1 Retornar Usuários
| Método | Rota | Descrição                  | 
| --- | --- |----------------------------|
|GET | /api/users | Retornar todos os Usuários |

##### Ordenação
```
/api/users?sort=id,desc
```
##### Paginação
```
/api/users?page=0&size=2
```
![image](https://user-images.githubusercontent.com/95001637/226931445-4d08df49-51af-4467-af42-883b50b662f7.png)

#

#### 1.2 Obter Usuário por ID

| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/users/{id} | Retornar um Usuário por id |

![image](https://user-images.githubusercontent.com/95001637/226931759-e38da416-b6c7-480c-a699-016e02e1e4d2.png)

#

#### 1.3 Obter Usuário com Endereços

| Método | Rota                      | Descrição                           |
|--------|---------------------------|-------------------------------------|
| GET    | /api/users/{id}/enderecos | Retornar o Usuário e seus Endereços |

![image](https://user-images.githubusercontent.com/95001637/226932197-c14f7374-0836-4408-b601-03e0ec796ccf.png)

#

#### 1.4 Cadastrar Usuário

| Método | Rota | Descrição            | JSON | 
| --- | --- |----------------------| --- | 
|POST | /api/users/ | Cadastrar um Usuário | <pre>{<br>  "nome": "João da Silva",<br/>  "email": "joao.silva@email.com",<br/>  "senha": "senha123",<br/>  "telefone": "(99) 9999-9999",<br/>  "dataNascimento": "1990-01-01",<br/>  "enderecos": [<br>     {<br>        "logradouro": "Rua Ulisses Lengruber", <br>        "cep": "28640-000",<br>        "numero": "383",<br>        "cidade": "Carmo"<br>      },  <br>      {<br>        "logradouro": "Rua Ulisses Lengruber",<br>        "cep": "28640-000",<br>        "numero": "175",<br>        "cidade": "Carmo"<br>      }<br>   ]<br>}</p>
</pre> |

| Nome  | Descrição   | 
| --- |-------------| 
|nome | Obrigatório |
|email | Obrigatório |
|senha | Obrigatório |
|telefone | Obrigatório |
|dataNascimento | Obrigatório |
|enderecos | Opcional    |

![image](https://user-images.githubusercontent.com/95001637/226937724-75d26360-5cfe-415d-910e-a4060f650cca.png)

#
#### 1.5 Atualizar Usuário

| Método | Rota            | Descrição            | JSON                                                                                     | 
|--------|-----------------|----------------------|------------------------------------------------------------------------------------------| 
| PUT    | /api/users/{id} | Atualizar um Usuário | <pre>{<br> "nome": "João Silva e Silva",<br> "email": joao.silva10@email.com <br>}</pre> |

| Nome  | Descrição   | 
| --- |-------------| 
|nome | Opcional    |
|email | Opcional |
|senha | Opcional |
|telefone | Opcional |
|dataNascimento | Opcional |

![image](https://user-images.githubusercontent.com/95001637/226941151-2522c58e-b35e-4aab-9336-945aa227d769.png)

#

#### 1.6 Deletar Usuário
| Método | Rota | Descrição              |
| --- | --- |------------------------|
|DEL | /api/users/{id} | Deletar Usuário por Id |

<hr>

### 2 Endereços

#### 2.1 Cadastrar Endereços
| Método | Rota               | Descrição                     | JSON |
|--------|--------------------|-------------------------------|------|
| POST   | /api/endereco/{id} | Cadastrar Endereco no Usuário | <pre>{<br>    "logradouro": "Rua Ulisses Lengruber", <br>    "cep": "28640-000",<br>    "numero": "383",<br>    "cidade": "Carmo"<br> }  <br></p>
 </pre> |


| Nome  | Descrição   | 
| --- |-------------| 
|logradouro | Obrigatório |
|cep | Obrigatório |
|numero | Obrigatório |
|cidade | Obrigatório |

![image](https://user-images.githubusercontent.com/95001637/227230671-7e4d94da-d96b-4242-b00f-d4e1872318b3.png)


#### 2.2 Atualizar Endereço

| Método | Rota               | Descrição                    | JSON |
|--------|--------------------|------------------------------|------|
| PUT    | /api/endereco/{id} | Atualizar Endereco | <pre>{<br>    "logradouro": "Rua Ulisses Lengruber", <br>    "cep": "28640-000",<br>    "numero": "383",<br>    "cidade": "Carmo"<br> }  <br></p>
 </pre> |

| Nome  | Descrição   |
| --- |-------------|
|logradouro | Opcional |
|cep | Opcional |
|numero | Opcional |
|cidade | Opcional |

![image](https://user-images.githubusercontent.com/95001637/227231418-5c92e5ec-66e4-429a-829a-3aa60f2ac5cc.png)

#

#### 2.3 Tornar Endereço Principal

| Método | Rota               | Descrição                 |
|--------|--------------------|---------------------------|
| PATCH  | /api/endereco/{id} | Tornar Endereço Principal |

![image](https://user-images.githubusercontent.com/95001637/227231805-1a79a821-e90e-47ed-8110-91f84e75ce5f.png)
![image](https://user-images.githubusercontent.com/95001637/227231929-efedfb64-b924-49c0-a69f-cb2801fc8465.png)
#

#### 2.4 Deletar Endereço

| Método | Rota | Descrição        | 
| --- | --- |------------------| 
|POST | /api/endereco/{id} | Deletar Endereço |

<hr>

### 2 Produto

#### 2.1 Cadastrar Produto
| Método | Rota         | Descrição         | JSON                                                                                                                                                                                                                                                                                                                                                                             |
|--------|--------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST   | /api/produto | Cadastrar Produto | <pre>{ <br>   "nome": "Nome do produto 1",<br>   "descricao": "Descrição do produto 1",<br>   "preco": 100,<br>   "categoria": "Categoria do produto 1",<br>   "marca": "Marca do produto 1",<br>   "images": [<br>     "https://www.example.com/image1.jpg",<br>     "https://www.example.com/image2.jpg",<br>     "https://www.example.com/image3.jpg"<br>   ] <br>}</p></pre> |


| Nome  | Descrição   | 
| --- |-------------| 
|nome | Obrigatório |
|descricao | Obrigatório |
|preco | Obrigatório |
|categoria | Obrigatório |
|marca | Obrigatório |
|images | Obrigatório |

![image](https://user-images.githubusercontent.com/95001637/227236428-92cfaa6f-9a34-40fe-9c24-969c75abff03.png)


#### 2.2 Atualizar Produto

| Método | Rota               | Descrição         | JSON |
|--------|--------------------|-------------------|------|
| PUT    | /api/produto{id} | Atualizar Produto | <pre>{<br>    "logradouro": "Rua Ulisses Lengruber", <br>    "cep": "28640-000",<br>    "numero": "383",<br>    "cidade": "Carmo"<br> }  <br></p>
 </pre> |

| Nome  | Descrição   |
| --- |-------------|
| nome | Opcional |
| descricao | Opcional |
| preco | Opcional |
| categoria | Opcional |
| marca | Opcional |
| images | Opcional |

![image](https://user-images.githubusercontent.com/95001637/227242920-c071058b-c8c4-4e73-8f31-56550450dde1.png)
#

#### 2.3 Obter Produtos

| Método | Rota            | Descrição                |
|--------|-----------------|--------------------------|
| GET    | /api/produto | Listar todos os produtos |

##### Ordenação
```
/api/produto?sort=id,desc
```
##### Paginação
```
/api/produto?page=0&size=2
```

![image](https://user-images.githubusercontent.com/95001637/227243156-34d272d1-1643-43b0-a5ec-bf58876870ce.png)

#

#### 2.4 Obter Produto por ID

| Método | Rota              | Descrição             | 
|--------|-------------------|-----------------------| 
| GET    | /api/produto/{id} | Listar produto por ID |

![image](https://user-images.githubusercontent.com/95001637/227243650-657d75f1-4187-42c3-a2b8-d2292ec30b51.png)

#### 2.5 Deletar Produto

| Método | Rota              | Descrição       | 
| --- |-------------------|-----------------| 
|POST | /api/produto/{id} | Deletar Produto |

<hr>

### 3 Carrinho

#### 3.1 Adicionar Produto ao Carrinho
| Método | Rota                  | Descrição               | JSON                                                                 |
|--------|-----------------------|-------------------------|----------------------------------------------------------------------|
| POST   | /api/produto/carrinho | Add produto ao carrinho | <pre>{ <br>   "produtoId": 2,<br>   "quantidade": 1,<br> }</p></pre> |

| Nome  | Descrição   |
| --- |-------------|
|produtoId | Obrigatório |
|quantidade | Obrigatório |

![image](https://user-images.githubusercontent.com/95001637/227244745-ee2c5f99-02aa-4607-8b29-229d68b27abe.png)

#

#### 3.2 Obter Carrinho

| Método | Rota              | Descrição                | 
|--------|-------------------|--------------------------| 
| GET    | /api/carrinho/itens | Listar itens do carrinho |

![image](https://user-images.githubusercontent.com/95001637/227244954-80c154f9-da00-4acb-b7f5-f54c4422eeba.png)

#

#### 3.3 Remover Item do Carrinho

| Método | Rota               | Descrição                | 
|--------|--------------------|--------------------------| 
| DEL    | /api/carrinho/{id} | Remover item do carrinho |

![image](https://user-images.githubusercontent.com/95001637/227245376-f3cfbc4f-4a38-4086-8c3c-970fdce556c4.png)

<hr>

### 4 Pedido

#### 4.1 Criar Pedido

| Método | Rota              | Descrição                                     | 
|--------|-------------------|-----------------------------------------------| 
| POST   | /api/pedido/novo | Criar um novo pedido com os itens do Carrinho |

![image](https://user-images.githubusercontent.com/95001637/227245945-c84c9414-8e5c-4bf3-a9b6-e6e877f6202d.png)

#

#### 4.2 Pagar Pedido

| Método | Rota                   | Descrição                                                     | 
|--------|------------------------|---------------------------------------------------------------| 
| POST   | /api/pedido/pagar/{id} | Pega os itens do pedido e cria um link de pagamento no PayPal |

![image](https://user-images.githubusercontent.com/95001637/227246363-eac5074a-bbf8-4dce-875c-cb54fff4ddff.png)

Após concluir o pagamento, o usuário é redirecionado para a página de sucesso e o Status do pedido é alterado para PAGO.

#

#### 4.3 Obter Pedidos

| Método | Rota              | Descrição              | 
|--------|-------------------|------------------------| 
| GET    | /api/pedido/pedidos | Obter lista de pedidos |

![image](https://user-images.githubusercontent.com/95001637/227251366-b7416bfe-5099-4fc7-9880-6879e4452b89.png)


