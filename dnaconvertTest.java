import static org.junit.jupiter.api.Assertions.*;

class dnaconvertTest {

    //Check if the standard Amino Acids(A/T/C/G)
    // are accepted and conv correctly
    @org.junit.jupiter.api.Test
    void transcriptionStandardAmino() {
        dnaconvert test = new dnaconvert();
        String input = "TACG";
        char[] charInput = input.toCharArray();
        String result = test.transcription(charInput);
        assertEquals(result,"AUGC");
    }

    //Check if the standard Amino Acids(A/U/C/G)
    // are accepted and conv correctly
    @org.junit.jupiter.api.Test
    void translationStandardAmino() {
        dnaconvert test = new dnaconvert();
        String input = "AUGC";
        String result = test.translation(input);
        assertEquals(result,"UACG");
    }

    //Given a divisible by 3 string
    //and containing valid amino acids(A/U/C/G)
    //And contains start Codon Methione(AUG)
    @org.junit.jupiter.api.Test
    void aminoAcidStartAndThree() {
        dnaconvert test = new dnaconvert();
        String input = "AUGAAA";
        String result = test.aminoAcid(input);
        assertEquals(result,"[Met, Lys]");
    }

    @org.junit.jupiter.api.Test
    //Given a divisible by 3 string
    //and containing valid amino acids(A/U/C/G)
    //And does not contain start Codon Methione(AUG)
    void aminoAcidNoStartAndThree() {
        dnaconvert test = new dnaconvert();
        String input = "AUAAAA";
        String result = test.aminoAcid(input);
        assertEquals(result,"[null, null]");
    }

    @org.junit.jupiter.api.Test
    //Given a string not divisible by 3
    //and containing valid amino acids(A/U/C/G)
    //And contains start Codon Methione(AUG)
    void aminoAcidStartAndNotThree() {
        dnaconvert test = new dnaconvert();
        String input = "AUGAA";
        String result = test.aminoAcid(input);
        assertEquals(result,"[Met, ]");
    }

    @org.junit.jupiter.api.Test
    //Given a string not divisible by 3
    //and containing valid amino acids(A/U/C/G)
    //And does not contain start Codon Methione(AUG)
    void aminoAcidNoStartAndNotThree() {
        dnaconvert test = new dnaconvert();
        String input = "AUAAA";
        String result = test.aminoAcid(input);
        assertEquals(result,"[null, null]");
    }

    //DNA String that contains Met(TAC)
    @org.junit.jupiter.api.Test
    void findStartMet() {
        dnaconvert test = new dnaconvert();
        String input = "TACAAA";
        String result = test.findStart(input);
        assertEquals(result,"TACAAA");
    }

    //DNA String that contains Met(TAC)
    //with one letter at the beginning
    @org.junit.jupiter.api.Test
    void findStartOneMet() {
        dnaconvert test = new dnaconvert();
        String input = "GTACAAA";
        String result = test.findStart(input);
        assertEquals(result,"TACAAA");
    }

    //DNA String that contains Met(TAC)
    //with two letters at the beginning
    @org.junit.jupiter.api.Test
    void findStartTwoMet() {
        dnaconvert test = new dnaconvert();
        String input = "GCTACAAA";
        String result = test.findStart(input);
        assertEquals(result,"TACAAA");
    }

    //DNA String that contains Met(TAC)
    //with three letters at the beginning
    @org.junit.jupiter.api.Test
    void findStartThreeMet() {
        dnaconvert test = new dnaconvert();
        String input = "GCATACAAA";
        String result = test.findStart(input);
        assertEquals(result,"TACAAA");
    }

    //DNA String that contains Met(TAC)
    //with three letters at the beginning
    @org.junit.jupiter.api.Test
    void findStartNoMet() {
        dnaconvert test = new dnaconvert();
        String input = "GCAAAA";
        String result = test.findStart(input);
        assertEquals(result,"");
    }

    //DNA String that contains only ATCG
    @org.junit.jupiter.api.Test
    void errorCheckingCorrect() {
        dnaconvert test = new dnaconvert();
        String input = "ATCGCTA";
        String result = test.errorChecking(input);
        assertEquals(result,"ATCGCTA");
    }

    //DNA String that contains something other than ATCG
    @org.junit.jupiter.api.Test
    void findStartIncorrect() {
        dnaconvert test = new dnaconvert();
        String input = "TAGTXZ";
        String result = test.errorChecking(input);
        assertEquals(result,"BADINPUT");
    }
}