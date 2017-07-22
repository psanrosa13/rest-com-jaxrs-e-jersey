import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import junit.framework.Assert;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.Servidor;
import br.com.alura.loja.modelo.Carrinho;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;


public class ClienteTeste {

	private HttpServer server;
	
	@Before
    public void before() {
       this.server = Servidor.inicializaServidor();
    }

    @After
    public void mataServidor() {
        server.stop();
    }
	
	
	@Test
	public void testaQueAConexaoComOServidorFunciona(){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://www.mocky.io");
		String conteudo= target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo= target.path("/carrinhos/1").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	
	}
	
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperadoJson() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo= target.path("/carrinhos/json/1").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = new Gson().fromJson(conteudo, Carrinho.class);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	
	}


}
