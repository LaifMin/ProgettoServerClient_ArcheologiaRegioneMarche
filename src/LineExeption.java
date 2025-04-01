public class LineExeption extends Exception {



    public LineExeption(int lineNumber) {
        super("Invalid data format in line: "+ lineNumber);
    }
}
