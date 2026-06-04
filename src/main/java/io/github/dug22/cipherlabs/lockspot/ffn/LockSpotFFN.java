package io.github.dug22.cipherlabs.lockspot.ffn;

import io.github.dug22.cipherlabs.lockspot.LockSpotModel;
import io.github.dug22.cipherlabs.utils.MathUtils;

public class LockSpotFFN extends LockSpotModel {

    private final LockSpotFFNModelData modelData;

    public LockSpotFFN(LockSpotFFNModelData modelData) {
        this.modelData = modelData;
    }

    public double[] getProbabilities(double[] inputs) {
        double[] currentLayer = inputs;
        double[][][] weights = modelData.weights;
        double[][] biases = modelData.biases;
        int weightsLength = weights.length;
        for (int i = 0; i < weightsLength; i++) {
            double[][] weight = weights[i];
            double[] bias = biases[i];
            double[] nextLayer = new double[bias.length];
            for (int nextNode = 0; nextNode < bias.length; nextNode++) {
                double sum = bias[nextNode];
                for (int currentNode = 0; currentNode < currentLayer.length; currentNode++) {
                    sum += currentLayer[currentNode] * weight[currentNode][nextNode];
                }
                if (i < weightsLength - 1) {
                    nextLayer[nextNode] = Math.max(0, sum);
                } else {
                    nextLayer[nextNode] = sum;
                }
            }

            currentLayer = nextLayer;
        }

        return MathUtils.getSoftMax(currentLayer);
    }



    public String[] getClasses() {
        return modelData.classes;
    }
}
