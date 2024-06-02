package com.example.rest.controller;

import com.example.rest.model.Cliente;
import com.example.rest.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllCliente() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> Cliente = clienteService.findById(id);
        return Cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        cliente.setFecha(LocalDate.EPOCH.atStartOfDay());
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        if (optionalCliente.isPresent()) {

            Cliente cliente = optionalCliente.get();
            cliente.setClienteid( (clienteDetails.getId() != null) ? clienteDetails.getId() : cliente.getId());
            cliente.setEstado( (clienteDetails.getEstado() != null) ? clienteDetails.getEstado() : cliente.getEstado());
            cliente.setContrasena( (clienteDetails.getContrasena() != null) ? clienteDetails.getContrasena() : cliente.getContrasena());

            cliente.setFecha(LocalDate.EPOCH.atStartOfDay());
            return ResponseEntity.ok(clienteService.save(cliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
