import java.util.*;

public class Test {



    static int cols = 15;
    static int rows = 16;

    public static void main(String[] args) {

        List<ColorCell> colorCells = createGame(rows, cols);
        Map<String , ColorCell> cellMap  = new HashMap<>();


        Map<String , ColorCell> largest = new HashMap<>();

        colorCells.forEach(cell -> {
            cellMap.put(cell.x + ":" + cell.y , cell);
        });

        System.out.println("******** Generated Color Grid *********");
        printGrid(cellMap);


        for (ColorCell cell : colorCells){
            Map<String , ColorCell> ar = new HashMap<>();
            findNeighbours(cell , cellMap , ar);

            if (largest.keySet().size() < ar.keySet().size()){
                largest = ar;
            }
        }


        System.out.println("********* Largest ********* ");

        printGrid(largest);





    }

    private static void findNeighbours(ColorCell cell , Map<String , ColorCell> cellMap ,  Map<String , ColorCell> ar ) {

        String cellKey = cell.x + ":" + cell.y;

        boolean cellReviewed = false;

        if (null != ar.get(cellKey)){
            cellReviewed = ar.get(cellKey).reviewed;
            ar.get(cellKey).reviewed = true;
        }

        //Left
        if (!cellReviewed && 0 < cell.y){
            String key = cell.x + ":" + (cell.y -1);

            ColorCell cellInMap = cellMap.get(key);

            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbours(cellInMap , cellMap , ar);
            }
        }

        //Right
        if (!cellReviewed && cols > cell.y + 1){
            String key = cell.x + ":" + (cell.y  + 1);
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbours(cellInMap , cellMap , ar);
            }
        }

        //Top
        if (!cellReviewed && 0 < cell.x){
            String key = (cell.x - 1) + ":" + cell.y;
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbours(cellInMap , cellMap , ar);
            }
        }

        //Bottom
        if (!cellReviewed && rows > cell.x + 1){
            String key = (cell.x  + 1) + ":" + cell.y;
            ColorCell cellInMap = cellMap.get(key);
            if (cellInMap.color == cell.color){
                ar.put(cellKey , cell);
                ar.put(key , cellInMap);

                findNeighbours(cellInMap , cellMap , ar);
            }
        }


    }


    static List<ColorCell> createGame(int col, int row) {
        int cells = col*row;

        List<ColorCell> colorCells = new ArrayList<>();

        for (int i = 0 ; i < cells ; i++){
            int x = i % col;
            int y = i / col;

            ColorCell cel = new ColorCell();
            cel.x = x;
            cel.y = y;
            cel.color = new Random().nextInt(3)+1;

            colorCells.add(cel);
        }

        return colorCells;
    }


    private static void printGrid(Map<String , ColorCell> cellMap){
        int [][] cells = new int[rows][cols];
        cellMap.forEach((key , cell ) -> {
            cells[cell.x][cell.y] = cell.color;
        });

        for (int xInd = 0 ; xInd < cells.length ; xInd++){
            for (int yInd = 0 ; yInd < cells[xInd].length ; yInd++){
                System.out.print(cells[xInd][yInd] + " ");
            }
            System.out.println("");
        }
    }



    public static class ColorCell{
        public int x;
        public int y;
        public int color;
        public boolean reviewed = false;
    }

}

