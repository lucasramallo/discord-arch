defmodule HermesWeb.ServerChannel do
  use HermesWeb, :channel

  @impl true
  def join("servers:" <> server_id, _params, socket) do
    # Verifique se o servidor existe (opcional)
    case exists?(server_id) do
      true ->
        {:ok, assign(socket, :server_id, server_id)}

      false ->
        {:error, %{reason: "Servidor não encontrado"}}
    end
  end

  @impl true
  def handle_in("mensagem", %{"conteudo" => conteudo}, socket) do
    # Broadcast para o servidor específico
    broadcast!(socket, "nova_mensagem", %{conteudo: conteudo})
    {:noreply, socket}
  end

  # It is also common to receive messages from the client and
  # broadcast to everyone in the current topic (session:lobby).
  @impl true
  def handle_in("shout", payload, socket) do
    broadcast(socket, "shout", payload)
    {:noreply, socket}
  end

  # Add authorization logic here as required.
  defp authorized?(_payload) do
    true
  end


  def exists?(server_id) do
    # Cheque no banco de dados ou em cache se o servidor existe
    true # Substitua por sua lógica
  end
end
