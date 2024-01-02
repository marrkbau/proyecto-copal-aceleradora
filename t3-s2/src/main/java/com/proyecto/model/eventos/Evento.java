package com.proyecto.model.eventos;

import com.proyecto.model.departamento.Departamento;
import com.proyecto.model.socio.ubicacion.Ubicacion;
import com.proyecto.persistencia.Persistencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")//VARCHAR(36) para almacenar UUIDs como cadenas
    @GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Getter
    @Setter
    @Column(name = "nombre")
    @NotNull(message = "Campo Obligatorio")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "descripcion")
    @NotNull(message = "Campo Obligatorio")
    private String descripcion;

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private List<ParticipanteEvento> participantes;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name="ubicacion_id")
    //@NotNull(message = "Campo Obligatorio")
    private Ubicacion ubicacion;

    @Getter
    @Setter
    @Column(name = "fecha_hora")
    @NotNull(message = "Campo Obligatorio")
    private LocalDateTime fechaHora;

    @Getter
    @Setter
    @ManyToMany
    private List<EstadoEvento> estados;

    @Getter
    @Setter
    @Column(name = "link")
    private String link;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "departamento_id", referencedColumnName = "id")
    //@NotNull(message = "Campo Obligatorio")
    private Departamento departamentoOrganizador;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoEvento tipo;

    @Getter
    @Setter
    @Column(name = "link_reunion")
    private String linkReunion;

    public Evento(){
        this.estados = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    public void agregarParticipante(ParticipanteEvento... participante){
        Collections.addAll(this.participantes, participante);
    }

    public EstadoEvento estadoActual(){
        return this.estados.get(this.estados.size()-1);
    }
}
