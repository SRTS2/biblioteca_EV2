package com.biblioteca.ms_prestamos.service;

import com.biblioteca.ms_prestamos.model.Prestamo;
import com.biblioteca.ms_prestamos.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {

    private PrestamoRepository repository;

    public PrestamoService(PrestamoRepository repository) {
        this.repository = repository;
    }

    public List<Prestamo> obtenerPrestamos() {
        return repository.findAll();
    }

    public Prestamo obtenerPrestamoPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Prestamo guardarPrestamo(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    public Prestamo actualizarPrestamo(Long id, Prestamo prestamo) {

        Prestamo prestamoExistente = repository.findById(id).orElse(null);

        if (prestamoExistente != null) {
            prestamoExistente.setUsuarioId(prestamo.getUsuarioId());
            prestamoExistente.setLibroId(prestamo.getLibroId());
            prestamoExistente.setFechaPrestamo(prestamo.getFechaPrestamo());

            return repository.save(prestamoExistente);
        }

        return null;
    }

    public void eliminarPrestamo(Long id) {
        repository.deleteById(id);
    }
}