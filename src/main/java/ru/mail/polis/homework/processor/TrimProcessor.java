package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor{

    private final ProcessingStage stage = ProcessingStage.POSTPROCESSING;
    private final int maxLength;

     public TrimProcessor(int maxLength) {
         this.maxLength = maxLength;
     }

     @Override
     public String processText(String text) {
         return text.substring(0, Math.min(maxLength, text.length()));
     }

     @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
