/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

/**
 *
 * @author feherbalint
 */
public class Filter {

    public double[] load(short[][] array, int x, int numOfLines) {
        double[] aValues = {1.0, 3.991983031580645, 5.975981215005254, -3.976013259934955, 0.992015076598585};;
        double[] bValues = {0.995999536444965, -3.983998145779859, 5.975997218669789, -3.983998145779859, 0.995999536444965};
        double[] filteredArray = new double[numOfLines];

        for (int n = 0; n < numOfLines; n++) {
            switch (n) {
                case 0:
                    filteredArray[n] = (bValues[0] * array[x][n]);
                    break;
                case 1:
                    filteredArray[n] = ((bValues[0] * array[x][n]) + (bValues[1] * array[x][n - 1]) + (aValues[1] * filteredArray[n - 1]));
                    break;
                case 2:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2]);
                    break;
                case 3:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + bValues[3] * array[x][n - 3] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3]);
                    break;
                default:
                    filteredArray[n] = (bValues[0] * array[x][n] + bValues[1] * array[x][n - 1] + bValues[2] * array[x][n - 2] + bValues[3] * array[x][n - 3] + bValues[4] * array[x][n - 4] + aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3] - aValues[4] * filteredArray[n - 4]);
                    break;
            }
            System.out.println("original: " + array[x][n]);
            System.out.println("filtered: " + filteredArray[n]);

        }

        return filteredArray;

    }

}
