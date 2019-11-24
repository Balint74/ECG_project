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

    double[] aValuesHighPass = {1.0, -3.991983031580645, 5.975981215005254, -3.976013259934955, 0.992015076598585};
    double[] bValuesHighPass = {0.995999536444965, -3.983998145779859, 5.975997218669789, -3.983998145779859, 0.995999536444965};

    double[] aValuesLowPass = {1.0, -3.359405101313432, 4.275509077139395, -2.438998657437775, 0.525578974468844};
    double[] bValuesLowPass = {1.677683035644570e-04, 6.710732142578280e-04, 0.001006609821387, 6.710732142578280e-04, 1.677683035644570e-04};

    public double[] highPass(double[] array) {

        double[] filteredArray = new double[array.length];

        filteredArray = filt(array, aValuesHighPass, bValuesHighPass, true);
        ArrayUtils.reverse(filteredArray);
        filteredArray = filt(filteredArray, aValuesHighPass, bValuesHighPass, true);
        ArrayUtils.reverse(filteredArray);

        return filteredArray;

    }

    public double[] lowPass(double[] array) {

        double[] filteredArray = new double[array.length];

        filteredArray = filt(array, aValuesLowPass, bValuesLowPass, false);
        ArrayUtils.reverse(filteredArray);
        filteredArray = filt(filteredArray, aValuesLowPass, bValuesLowPass, false);
        ArrayUtils.reverse(filteredArray);

        return filteredArray;

    }

    double[] filt(double[] array, double[] aValues, double[] bValues, boolean isIthighPass) {

        double[] filteredArray = new double[array.length];

        for (int n = 0; n < array.length; n++) {

            double elotag = 0;
            double utotag = 0;

            for (int k = 1; k < 5; k++) {
                if (0 <= (n - k)) {
                    elotag += aValues[k] * filteredArray[n - k];
                }

            }
            for (int k = 0; k < 5; k++) {
                if (0 <= (n - k)) {
                    utotag += bValues[k] * array[n - k];
                }

            }

            filteredArray[n] = utotag - elotag;
            //System.out.println(n + ".tag: " + filteredArray[n]);

        }

        return filteredArray;
    }
}

/*

         


 */
