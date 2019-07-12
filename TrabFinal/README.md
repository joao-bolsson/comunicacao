
A aplicação contém um Server central que apenas faz o encaminhamento de mensagens que chegam até ele para os outros clientes conectados ao chat.

### Executando

- Execute uma vez a classe Server
Isso fará com que um Server seja criado e fique ouvindo a porta 12345.

- Execute 2 vezes a classe Client, apenas altere o nome "Cliente" para um de sua preferência, apenas para diferenciar uma janela da outra no chat.

Temos 2 clientes conectados ao servidor. Quando um enviar uma mensagem o servidor vai encaminhar para o outro sem fazer verificações, apenas o encaminhamento em si.

##### Frame

Essa classe simula um Frame com uma mensagem. Existem métodos para transformá-lo em um array de bytes (para envio para o receptor) e o processo inverso de bytes para caracteres (para a reconstrução da mensagem no receptor).

##### Client

A classe Client recebe uma mensagem digitada pelo usuário e envia em forma de bytes para Server que a encaminha para o outro cliente. Para evitar erros de checksum, a mensagem é quebrada em várias partes iguais com tamanho igual a 2 bytes (2 caracteres). O checksum é feito com somando esses dois bytes e complementando invertendo o sinal. Assim, quando a mensagem chegar ao receptor podemos apenas somar todos os valores recebidos e verificar se o resultado é zero. Se foi igual à zero, envia um ACK de confirmação "ok=id", onde id, seria a identificação do frame. Se a mensagem estiver certa reconstrói os bytes recebidos para caracteres e armazena em um buffer que será entregue para a interface em ordem para a reconstrução da mensagem completa.

Stop-and-wait

No lado do emissor, quebramos a palavra, como foi dito anteriormente e fazemos o processo de construção do que será enviado para o receptor: os bytes junto com o checksum. Quando enviamos o frame adicionamos uma tarefa para verificar o frame que foi enviado após 2s. Se o timer disparar, verifica se o frame foi recebido com sucesso, se foi, apenas finaliza a tarefa, se não, envia novamente o frame daquela task.

Selective Repeat

Ao enviar a mensagem, criamos um array com os frames para envio. Ao final do envio, verificamos os frames que não foram enviados e tentamos enviar novamente.

O timer executa em uma thread separada do restante da aplicação, portanto, podemos juntar o stop-and-wait ARQ com o selective repeat.

