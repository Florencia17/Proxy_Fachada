package punto1.modelo;

import java.util.Set;

public interface InterfazDao {

    Persona personaPorId(int id);

    Set<Telefono> obtenerTelefonosPorIdPersona(int idPersona);

}
