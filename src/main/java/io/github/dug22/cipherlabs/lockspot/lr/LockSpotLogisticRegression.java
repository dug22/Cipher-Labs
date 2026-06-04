package io.github.dug22.cipherlabs.lockspot.lr;

import io.github.dug22.cipherlabs.lockspot.LockSpotModel;
import io.github.dug22.cipherlabs.utils.MathUtils;

import java.util.stream.IntStream;

public class LockSpotLogisticRegression extends LockSpotModel {


    private final LockSpotLogisticRegressionModelData modelData;

    public LockSpotLogisticRegression(LockSpotLogisticRegressionModelData modelData) {
        this.modelData = modelData;
    }

    public double[] getProbabilities(double[] inputs) {
        double[][] weights = modelData.weights;
        double[] intercepts = modelData.intercepts;
        double[] logits = new double[weights.length];
        IntStream.range(0, weights.length).forEach(i -> {
            logits[i] = intercepts[i];
            IntStream.range(0, inputs.length).forEach(j -> {
                logits[i] += inputs[j] * weights[i][j];
            });
        });
        return MathUtils.getSoftMax(logits);
    }

    public String[] getClasses() {
        return modelData.classes;
    }
}
