import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MatrixMultiplier {
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    public static void writeMatrixToFile(int[][] matrix, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                writer.write(String.valueOf(matrix[i][j]));
                writer.write(" ");
            }
            writer.write(System.lineSeparator());
        }

        writer.close();
    }

    public static int[][] readMatrixFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        int[][] matrix;

        String[] lines = Files.readAllLines(Paths.get(filePath)).toArray(new String[0]);
        int rows = lines.length;
        int cols = lines[0].split(" ").length;
        matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] values = lines[i].split(" ");
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
        }

        return matrix;
    }

    @Test
    public void testMultiplyMatrices() {
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{5, 6}, {7, 8}};
        int[][] expected = {{19, 22}, {43, 50}};

        int[][] result = MatrixMultiplier.multiplyMatrices(matrix1, matrix2);
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testReadWriteMatrix() throws IOException {
        int[][] matrix = {{1, 2}, {3, 4}};
        String filePath = "matrix.txt";

        MatrixMultiplier.writeMatrixToFile(matrix, filePath);
        int[][] result = MatrixMultiplier.readMatrixFromFile(filePath);

        Assertions.assertArrayEquals(matrix, result);

        Files.deleteIfExists(Paths.get(filePath));
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        int[][] matrix1 = {{1, 2, 3}, {4, 5, 6}};
        int[][] matrix2 = {{7, 8}, {9, 10}, {11, 12}};

        int[][] result = multiplyMatrices(matrix1, matrix2);

        try {
            writeMatrixToFile(result, "result.txt");
            System.out.println("Resultado guardado en el archivo 'result.txt'.");
        } catch (IOException e) {
            System.out.println("Error al guardar el resultado: " + e.getMessage());
        }
    }
}