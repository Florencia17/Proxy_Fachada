package punto1.persistencia;

import punto1.modelo.InterfazDao;
import punto1.modelo.Persona;
import punto1.modelo.Proxy;
import punto1.modelo.Telefono;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PersonaDao implements InterfazDao {

    private Connection obtenerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/proxy_malacarne";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public Persona personaPorId(int idPersona) {
        String sql = "select nombre "
                + "from personas "
                + "where id = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            ResultSet result = statement.executeQuery();
            String nombrePersona = null;
            while (result.next()) {
                nombrePersona = result.getString("nombre");
            }
            return new Persona(idPersona, nombrePersona, new Proxy(this, idPersona));
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido obtener la persona", e);
        }
    }

    public Set<Telefono> obtenerTelefonosPorIdPersona(int idPersona) {
        String sql = "select numero "
                + "from telefonos "
                + "where idPersona = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            ResultSet result = statement.executeQuery();
            Set<Telefono> telefonos = new HashSet<>();
            while (result.next()) {
                telefonos.add(new Telefono(result.getString("numero")));
            }
            return telefonos;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido obtener los telefonos", e);
        }
    }


}
