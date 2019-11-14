/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecg_project;

import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author feherbalint
 */
public class Filter {

    double[] aValuesHighPass = {1.0, 3.991983031580645, 5.975981215005254, -3.976013259934955, 0.992015076598585};
    double[] bValuesHighPass = {0.995999536444965, -3.983998145779859, 5.975997218669789, -3.983998145779859, 0.995999536444965};

    double[] aValuesLowPass = {1.0, 3.359405101313432, 4.275509077139395, -2.438998657437775, 0.525578974468844};
    double[] bValuesLowPass = {1.677683035644570e-04, 6.710732142578280e-04, 0.001006609821387, 6.710732142578280e-04, 1.677683035644570e-04};

    public double[] highPass(double[] array, int x, int numOfLines) {

        double[] filteredArray = new double[numOfLines];

        filteredArray = filt(array, numOfLines, aValuesHighPass, bValuesHighPass);
        ArrayUtils.reverse(filteredArray);
        filteredArray = filt(filteredArray, numOfLines, aValuesHighPass, bValuesHighPass);
        ArrayUtils.reverse(filteredArray);

        return filteredArray;

    }

    public double[] lowPass(double[] array, int x, int numOfLines) {

        double[] filteredArray = new double[numOfLines];

        filteredArray = filt(array, numOfLines, aValuesLowPass, bValuesLowPass);
        ArrayUtils.reverse(filteredArray);
        filteredArray = filt(filteredArray, numOfLines, aValuesLowPass, bValuesLowPass);
        ArrayUtils.reverse(filteredArray);

        return filteredArray;

    }

    double[] filt(double[] array, int numOfLines, double[] aValues, double[] bValues) {
        double[] filteredArray = new double[numOfLines];
        /*
        for (int n = 0; n < numOfLines; n++) {
            switch (n) {
                case 0:
                    filteredArray[n] = bValues[0] * array[n];
                    break;
                case 1:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] - -aValues[1] * filteredArray[n - 1];
                    break;
                case 2:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] + bValues[2] * array[n - 2] - -aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2];
                    break;
                case 3:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] + bValues[2] * array[n - 2] + bValues[3] * array[n - 3] - -aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3];
                    break;
                default:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] + bValues[2] * array[n - 2] + bValues[3] * array[n - 3] + bValues[4] * array[n - 4] - -aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3] - aValues[4] * filteredArray[n - 4];
                    break;
            }

        }
         */
        for (int n = 0; n < numOfLines; n++) {
            switch (n) {
                case 0:
                    filteredArray[n] = bValues[0] * array[n];
                    break;
                case 1:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] - -aValues[1] * filteredArray[n - 1];
                    break;
                case 2:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] + bValues[2] * array[n - 2] - -aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2];
                    break;
                case 3:
                    filteredArray[n] = bValues[0] * array[n] + bValues[1] * array[n - 1] + bValues[2] * array[n - 2] + bValues[3] * array[n - 3] - -aValues[1] * filteredArray[n - 1] - aValues[2] * filteredArray[n - 2] - aValues[3] * filteredArray[n - 3];
                    break;
                default:

                    double elotag = 0;
                    double utotag = 0;

                    for (int k = 2; k < 5; k++) {
                        elotag -= aValues[k] * filteredArray[n - k];
                    }
                    for (int k = 0; k < 5; k++) {
                        utotag += bValues[k] * array[n - k];
                    }

                    filteredArray[n] = elotag + aValues[1] * filteredArray[n - 1] + utotag;
                    System.out.println(filteredArray[n]);
                    break;
            }

        }

        return filteredArray;
    }
}

/*

         


 */
