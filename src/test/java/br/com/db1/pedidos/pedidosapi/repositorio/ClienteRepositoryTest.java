package br.com.db1.pedidos.pedidosapi.repositorio;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.db1.pedidos.pedidosapi.domain.Cliente;
import br.com.db1.pedidos.pedidosapi.domain.StatusCliente;

//classe de teste de integração
	@RunWith(SpringRunner.class)
	@SpringBootTest 
	public class ClienteRepositoryTest {
		
		//injeção de independencia
			@Autowired
			private ClienteRepository clienteRepository;
			
			@After
			public void after() {
				clienteRepository.deleteAll();
			}
			
			@Test
			public void deveSalvarUmCliente() {
				Cliente cliente = new Cliente("Maiko Cunha", "99999999999");
				
				clienteRepository.save(cliente);
				
				long count = clienteRepository.count();
				
				Assert.assertEquals(1, count);
			}
			
			@Test
			public void deveSalvarClienteAlterado() {
				
				Cliente cliente = new Cliente("Maiko Cunha", "99999999999");
				
				clienteRepository.save(cliente);
				
				Cliente clienteSalvo = clienteRepository.findByCpf("99999999999");
				
				clienteSalvo.inativar();
				
				clienteRepository.save(clienteSalvo);
				
				Cliente clienteAlterado = clienteRepository.findByCpf("99999999999");
				
				Assert.assertEquals(StatusCliente.INATIVO, clienteAlterado.getStatus());
				
			}
}
