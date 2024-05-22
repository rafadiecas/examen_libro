package com.example.examen_libro;

import com.example.examen_libro.enumerados.Pasillos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.time.LocalDate;

public class Controlador {
    @FXML private TextField autor;
    @FXML private TextField titulo;
    @FXML private TextField isbn;
    @FXML private TextField edicion;
    @FXML private TextField publicacion;
    @FXML private TextField editorial;
    @FXML private TextField paginas;
    @FXML private TextField stock;
    @FXML private TextField pvp;
    @FXML private ComboBox<String> pasillo;
    @FXML private DatePicker anyo_pub;
    @FXML private DatePicker anyo_ed;
    @FXML private TextField filtro_isbn;
    @FXML private TextField filtro_nombre;
    @FXML public TableView<Libro> tabla;
    @FXML private TableColumn<Libro, String> autor_col;
    @FXML private TableColumn<Libro, String> titulo_col;
    @FXML private TableColumn<Libro, String> isbn_col;
    @FXML private TableColumn<Libro, String> edicion_col;
    @FXML private TableColumn<Libro, String> publicacion_col;
    @FXML private TableColumn<Libro, String> editorial_col;
    @FXML private TableColumn<Libro, Integer> paginas_col;
    @FXML private TableColumn<Libro, Integer> stock_col;
    @FXML private TableColumn<Libro, Float> precio_col;
    @FXML private TableColumn<Libro, String> pasillo_col;
    @FXML private TableColumn<Libro, LocalDate> anyoPublicacion_col;
    @FXML private TableColumn<Libro, LocalDate> anyoEdicion_col;
    @FXML private AnchorPane panel_tabla;


    private FilteredList<Libro> filteredData;

    public Boolean modoEdicion = true;
    Integer id = 0;

    public void initialize() {
        ObservableList<Libro> data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data, s -> true);
        tabla.setItems(filteredData);
        cargarDatosTabla(data);
        pasillo.getItems().addAll("primero", "segundo", "tercero");
        panel_tabla.setVisible(true);


        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Libro libro = tabla.getSelectionModel().getSelectedItem();
                panel_tabla.setVisible(false);
                modoEdicion = true;
                autor.setText(libro.getAutor());
                titulo.setText(libro.getTitulo());
                isbn.setText(libro.getIsbn());
                edicion.setText(libro.getNumero());
                publicacion.setText(libro.getLugarPublicacion());
                editorial.setText(libro.getEditorial());
                paginas.setText(String.valueOf(String.valueOf(libro.getPaginas())));
                stock.setText(String.valueOf(libro.getStock()));
                pvp.setText(String.valueOf(libro.getPvp()));
                pasillo.setValue(libro.getPasillo().toString());
                stock.setText(String.valueOf(libro.getStock()));
                anyo_pub.setValue(libro.getAnyoPublicacion());
                anyo_ed.setValue(libro.getAnyoEdicion());
                sacaId(libro);
                modoEdicion = true;
            }
        });

        filtro_isbn.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarPorIsbn(newValue);
        });

        filtro_nombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarPorNombre(newValue);
        });
    }

    public Connection getConexion() {
        String url = "jdbc:mysql://localhost:3306/libros";
        String usuario = "rootedd";
        String contrasenya = "1234";
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasenya);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void cargarDatosTabla(ObservableList<Libro> data) {
        try (Connection conexion = getConexion()) {
            String consulta = "SELECT * FROM libro";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);

            while (resultSet.next()) {
                String autorText = resultSet.getString("autor");
                String numeroText = resultSet.getString("numero");
                String pubText = resultSet.getString("lugar_publicacion");
                String tituloText = resultSet.getString("titulo");
                String pasilloText = resultSet.getString("pasillo");
                String editorialText = resultSet.getString("editorial");
                Float pvp = resultSet.getFloat("pvp");
                Integer paginasText = resultSet.getInt("paginas");
                Integer stockText = Integer.valueOf(resultSet.getString("stock"));
                String isbnText = resultSet.getString("isbn");
                LocalDate anyoPub = resultSet.getDate("anyo_publicacion").toLocalDate();
                LocalDate anyoEd = resultSet.getDate("anyo_edicion").toLocalDate();

                data.add(new Libro(autorText, isbnText, numeroText, pubText, tituloText, Pasillos.valueOf(pasilloText), editorialText, paginasText, pvp, stockText, anyoPub, anyoEd));
            }

            autor_col.setCellValueFactory(new PropertyValueFactory<>("autor"));
            edicion_col.setCellValueFactory(new PropertyValueFactory<>("numero"));
            publicacion_col.setCellValueFactory(new PropertyValueFactory<>("lugarPublicacion"));
            anyoPublicacion_col.setCellValueFactory(new PropertyValueFactory<>("anyoPublicacion"));
            anyoEdicion_col.setCellValueFactory(new PropertyValueFactory<>("anyoEdicion"));
            titulo_col.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            pasillo_col.setCellValueFactory(new PropertyValueFactory<>("pasillo"));
            editorial_col.setCellValueFactory(new PropertyValueFactory<>("editorial"));
            precio_col.setCellValueFactory(new PropertyValueFactory<>("pvp"));
            paginas_col.setCellValueFactory(new PropertyValueFactory<>("paginas"));
            isbn_col.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            stock_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void nuevo() {
        panel_tabla.setVisible(false);
        modoEdicion = false;
    }

private void sacaId(Libro libro) {
    try (Connection conexion = getConexion()) {
        String consulta = "SELECT id FROM libro WHERE isbn = ?";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, libro.getIsbn());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    private void filtrarPorIsbn(String filtro) {
        filteredData.setPredicate(libro -> {
            if (filtro == null || filtro.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filtro.toLowerCase();
            return libro.getIsbn().toLowerCase().contains(lowerCaseFilter);
        });
    }

    private void filtrarPorNombre(String filtro) {
        filteredData.setPredicate(libro -> {
            if (filtro == null || filtro.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filtro.toLowerCase();
            return libro.getTitulo().toLowerCase().contains(lowerCaseFilter);
        });
    }

    public void guardar() {
        if (modoEdicion) {
            try (Connection conexion = getConexion()) {
                String consulta = "UPDATE libro SET autor = ?, titulo = ?, numero = ?, lugar_publicacion = ?, editorial = ?,isbn = ?, paginas = ?, pvp = ?, stock = ?, pasillo = ?, anyo_publicacion = ?, anyo_edicion = ? WHERE id = ?";
                PreparedStatement statement = conexion.prepareStatement(consulta);
                statement.setString(1, autor.getText());
                statement.setString(2, titulo.getText());
                statement.setString(3, edicion.getText());
                statement.setString(4, publicacion.getText());
                statement.setString(5, editorial.getText());
                statement.setString(6, isbn.getText());
                statement.setInt(7,Integer.parseInt(paginas.getText()));
                statement.setDouble(8, Double.parseDouble(pvp.getText()));
                statement.setInt(9,Integer.parseInt(stock.getText()));
                statement.setString(10, pasillo.getValue().toString());
                statement.setDate(11, Date.valueOf(anyo_pub.getValue()));
                statement.setDate(12, Date.valueOf(anyo_ed.getValue()));
                statement.setInt(13, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (Connection conexion = getConexion()) {
                String consulta = "INSERT INTO libro (autor, titulo, numero, lugar_publicacion, editorial, isbn, paginas, pvp, stock,pasillo,anyo_publicacion,anyo_edicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
                PreparedStatement statement = conexion.prepareStatement(consulta);
                statement.setString(1, autor.getText());
                statement.setString(2, titulo.getText());
                statement.setString(3, edicion.getText());
                statement.setString(4, publicacion.getText());
                statement.setString(5, editorial.getText());
                statement.setString(6, isbn.getText());
                statement.setInt(7,Integer.parseInt(paginas.getText()));
                statement.setDouble(8, Double.parseDouble(pvp.getText()));
                statement.setInt(9,Integer.parseInt(stock.getText()));
                statement.setString(10, pasillo.getValue().toString());
                statement.setDate(11, Date.valueOf(anyo_pub.getValue()));
                statement.setDate(12, Date.valueOf(anyo_ed.getValue()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        ObservableList<Libro> data = FXCollections.observableArrayList();
        cargarDatosTabla(data);
        filteredData = new FilteredList<>(data, s -> true);
        tabla.setItems(filteredData);
        panel_tabla.setVisible(true);
        id = 0;
    }





}