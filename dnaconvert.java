/*		Purpose
In Biology, DNA replicates via transcription and translation which consists of matching base pairs 
and using charts to find resulting amino acids. This process can be tedious if done manually.
This program, using Java, reduces the time of converting the DNA strand to its mRNA and tRNA counterparts and removes any human error.
The program also provides the sequence of amino acids that results from that specific DNA strand, instantly,
such that the user does not have to waste time performing base pair conversions and looking at charts.
- Varun Zaver
*/

import java.util.Scanner;
import java.io.*;
import java.util.Arrays;

public class dnaconvert{
	public static void main(String[] args){
		//Variables
		String input;				//User original input
		Scanner keyboard = new Scanner(System.in);
		boolean flag = true;		//True if = ATCG. False if otherwise
		char[] inpCharArr;
		char[] noStartTransArr;
		String clean;				//User input but easier to manipulate.
		String correctedInput;		//User input but modified to start at correct spot

		//Hacker stuff
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("\t\t\tWelcome to Varun's DNA Conversion Center!");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.print("Insert the strand of DNA: \t\t");
		
		input = keyboard.nextLine();			//Accept input from user

		input = errorChecking(input);

		if(input.equals("BADINPUT")){
			System.out.println("The string you've inputted is not legal. Goodbye.");
			return;		//End the program
		}

		correctedInput = findStart(input);			//Give input and fix it so it starts with the Start Codon (DNA STILL)
		
		if(correctedInput.equals("")){
			System.out.println("Input DNA string does not contain start codon (TAC)");
			noStartTransArr = input.toCharArray();
			String noStartTrans = transcription(noStartTransArr);
			System.out.println("Transcribing anyways:");
			System.out.println("\tComplimentary mRNA Strand\t" + noStartTrans);
		}else{

			inpCharArr = correctedInput.toCharArray();		//Make input a char array to iterate through
			System.out.println("Input editted to start at start codon: \t" + correctedInput);

			String mRNAStrand = transcription(inpCharArr);							//Get mRNA strand
			System.out.println("Complimentary mRNA strand: \t\t" + mRNAStrand);	//Print complimentary mRNA strand
		
			String tRNAStrand = translation(mRNAStrand);
			System.out.println("Complimentary tRNA strand: \t\t" + tRNAStrand);	//Print complimentary tRNA strand
		
			//System.out.println("There exists a start codon in the string\nLook for AUG to match Methionine");

			String amino = aminoAcid(mRNAStrand);
			char [] aminoArr = amino.toCharArray();
			System.out.println("The Amino Acid Sequence that this DNA strand consists of is: " + amino);
		
		}

	}
	
	//Methods
	
	//Input: User DNA strand
	//Output: Compliment mRNA strand as String
	//Purpose?: Create a mRNA Strand 
	public static String transcription(char[] dnaStrand){
		int userLength = dnaStrand.length;
		char[] mRNA;	//Complimentary strand
		mRNA = new char[userLength];		//mRNA strand now equal in size to DNA strand
	
		for(int i = 0; i < userLength; i++){
			if(dnaStrand[i] == 'A'){		//If nucleotide is Adenine
				mRNA[i] = 'U';				//Adenine binds with Uracil
			}else if(dnaStrand[i] == 'T'){	//etc..
				mRNA[i] = 'A';
			}else if(dnaStrand[i] == 'C'){
				mRNA[i] = 'G';
			}else if(dnaStrand[i] == 'G'){
				mRNA[i] = 'C';
			}
		}
		
		String mRNAStrand = new String(mRNA);	//Convert the char array to a string
		return mRNAStrand;							//Return the mRNA string
	}

	//Input: Compliment mRNA strand
	//Output: Compliment tRNA strand as String
	//Purpose?:	Create a tRNA strand
	public static String translation(String mRNA){
		int userLength = mRNA.length();
		char[] tRNA;			//Complimentary Strand
		tRNA = new char[userLength];		//tRNA now the size of mRNA
		for(int i = 0; i < mRNA.length(); i++){
			if(mRNA.charAt(i) == 'U'){
				tRNA[i] = 'A';
			}else if(mRNA.charAt(i) == 'A'){
				tRNA[i] = 'U';
			}else if(mRNA.charAt(i) == 'C'){
				tRNA[i] = 'G';
			}else if(mRNA.charAt(i) == 'G'){
				tRNA[i] = 'C';
			}
		}
		String tRNAStrand = String.valueOf(tRNA);	//Convert the char array to a string
		return tRNAStrand;							//Return the tRNA string
	}
	
	//Input: Compliment mRNA strand
	//Output: Amino Acid sequence
	//Purpose?:	Create a sequence of Amino Acids
	public static String aminoAcid(String mRNA){
		//concatenate mRNA[0],[1],[2] into one string and compare that string to codons on codon chart
		//move to mRNA[3], and repeat above process
		int userLength = mRNA.length();			//Total number of nucleotides
		int seqLength = userLength / 3;			//Ex: 6 nucleotides = 2 codons = 2 aminoAcids. 6/3 = 2
		
		int offset = userLength % 3;			//Finds how many nucleotides remain after finding number of codons

		if(offset == 0){
			//seqLength is a multiple of 3
		}else if(offset == 1){
			//2 off so add 1 spots to userLength
			userLength += 2;
			//2 off so add 1 spot for protien
			seqLength++;

			//mRNA.length() = 2 + mRNA.length();
		}else if(offset == 2){
			//1 off so add 
			userLength += 1;
			//2 off so add 1 spot to protein
			seqLength++;
			//mRNA.length() = 1 + mRNA.length();
		}
		char[] iterArr = new char[userLength];

		for(int i = 0; i < mRNA.length(); i++){
			iterArr[i] = mRNA.charAt(i);
		}

		String[] aminoAcidSeq = new String[seqLength];		//Allocate enough spots for peptide chain
		int location = 0;
		boolean start = false;					//True = Met found and find other AAs. False = no Met so dont look for AAs
		char[] nucleo = new char[3];	//Have to make a char array and not string bc string are immutable
		//CONVERT 3 CHARS INTO A STRING. PUT INTO A ASTRING ARRAY
		//ITERATE THRU THAT STRING ARRAY AND COMPARE TO CODONS.
		
		//if mrna % 3 is not 0 then increase size of mrna such that mrna%3 =0; this is what well. 
		
		for(int k = 0; k < userLength; k+=3){
			nucleo[0] = iterArr[k];
			nucleo[1] = iterArr[k+1];
			nucleo[2] = iterArr[k+2];
			String codon = String.valueOf(nucleo);		//Convert the 3 chars into a string(Codon)
			//System.out.println(codon);

			if(codon.equals("AUG")){
				start=true;
			}
			
		if(start){	
			if(codon.equals("UUU") || codon.equals("UUC")){
				//Phe
				aminoAcidSeq[location] = "Phe";
				location+=1;
				//System.out.println("Phe");
				//System.out.println(aminoAcidSeq);			Incorrect because we need to convert the String[] to a String data type
			}else if(codon.equals("UUA") || codon.equals("UUG") || codon.equals("CUU") || codon.equals("CUC") || codon.equals("CUA") || codon.equals("CUG")){
				//Leu
				aminoAcidSeq[location] = "Leu";
				location+=1;
				//System.out.println("Leu");
			}else if(codon.equals("AUU") || codon.equals("AUC") || codon.equals("AUA")){
				//Ile
				aminoAcidSeq[location] = "Ile";
				location+=1;
				//System.out.println("Ile");
			}else if(codon.equals("AUG")){
				//Met
				aminoAcidSeq[location] = "Met";
				location+=1;
				//System.out.println("Met");
			}else if(codon.equals("GUU") || codon.equals("GUC") || codon.equals("GUA") || codon.equals("GUG") ){
				//Val
				aminoAcidSeq[location] = "Val";
				location+=1;
				//System.out.println("Val");
			}else if(codon.equals("UCU") || codon.equals("UCC") || codon.equals("UCA") ||codon.equals("UCG") || codon.equals("AGU") || codon.equals("AGC")){
				//Ser
				aminoAcidSeq[location] = "Ser";
				location+=1;
				//System.out.println("Ser");
			}else if(codon.equals("CCU") || codon.equals("CCC") || codon.equals("CCA") || codon.equals("CCG")){
				//Pro
				aminoAcidSeq[location] = "Pro";
				location+=1;
				//System.out.println("Pro");
			}else if(codon.equals("ACU") || codon.equals("ACC") || codon.equals("ACA") || codon.equals("ACG")){
				//Thr
				aminoAcidSeq[location] = "Thr";
				location+=1;
				//System.out.println("Thr");
			}else if(codon.equals("GCU") || codon.equals("GCC") || codon.equals("GCA") || codon.equals("GCG")){
				//Ala
				aminoAcidSeq[location] = "Ala";
				location+=1;
				//System.out.println("Ala");
			}else if(codon.equals("UAU") || codon.equals("UAC")){
				//Tyr
				aminoAcidSeq[location] = "Tyr";
				location+=1;
				//System.out.println("Tyr");
			}else if(codon.equals("UAA") || codon.equals("UAG") || codon.equals("UGA")){
				//Stop
				aminoAcidSeq[location] = "Stop";
				location+=1;
				//Amino Acid searching is done. No need to keep looking.
				start = false;
				//System.out.println("Stop");
			}else if(codon.equals("CAU") || codon.equals("CAC")){
				//His
				aminoAcidSeq[location] = "His";
				location+=1;
				//System.out.println("His");
			}else if(codon.equals("CAA") || codon.equals("CAG")){
				//Gln
				aminoAcidSeq[location] = "Gln";
				location+=1;
				//System.out.println("Gln");
			}else if(codon.equals("AAU") || codon.equals("AAC")){
				//Asn
				aminoAcidSeq[location] = "Asn";
				location+=1;
				//System.out.println("Asn");
			}else if(codon.equals("AAA") || codon.equals("AAG")){
				//Lys
				aminoAcidSeq[location] = "Lys";
				location+=1;
				//System.out.println("Lys");
			}else if(codon.equals("GAU") || codon.equals("GAC")){
				//Asp
				aminoAcidSeq[location] = "Asp";
				location+=1;
				//System.out.println("Asp");
			}else if(codon.equals("GAA") || codon.equals("GAG")){
				//Glu
				aminoAcidSeq[location] = "Glu";
				location+=1;
				//System.out.println("Glu");
			}else if(codon.equals("UGU") || codon.equals("UGC")){
				//Cys
				aminoAcidSeq[location] = "Cys";
				location+=1;
				//System.out.println("Cys");
			}else if(codon.equals("UGG")){
				//Trp
				aminoAcidSeq[location] = "Trp";
				location+=1;
				//System.out.println("Trp");
			}else if(codon.equals("CGU") || codon.equals("CGC") || codon.equals("CGA") || codon.equals("CGG") || codon.equals("AGA") || codon.equals("AGG")){
				//Arg
				aminoAcidSeq[location] = "Arg";
				location+=1;
				//System.out.println("Arg");
			}else if(codon.equals("GGU") || codon.equals("GGC") || codon.equals("GGA") || codon.equals("GGG")){
				//Gly
				aminoAcidSeq[location] = "Gly";
				location+=1;
				//System.out.println("Gly");
			}else{
				aminoAcidSeq[location] = "";
				location+=1;
			}
		}//if statement	
		}//for loop
		String tom = Arrays.toString(aminoAcidSeq);
		//System.out.println(tom);
		return tom;
	}

	//Input: DNA string that the user inputted
	//Output: DNA strand that starts at TAC
	//Purpose: Adjust input string such that new string begins with starting codon.
	public static String findStart(String input){
		//Convert string input into char array
		char[] charArr = input.toCharArray();	//Inputted string as a char array
		int dnaLength = charArr.length;			//This will get us the length of the DNA strand inputted
		String trueDNAStrand = "";

		for(int i = 0; i < dnaLength; i++){
			if(charArr[i] == 'T'){	//Check for TAC which will be translated to AUG
				if( ( (i+1) < dnaLength ) && (charArr[i+1] == 'A')){
					if( ( (i+2) < dnaLength ) && (charArr[i+2] == 'C')) {
						//AUG codon has been found.
						//Now we want to shrink the array by getting rid off all characters before the A in the AUG codon
						//Create a new array that is the size of charArr to be charArr.length - i
						//and copy the contents from i to the end of charArr into the newly made array
						char[] trueDNA = new char[dnaLength-i];		//Set up new array so that it's length is just enough to get all the nucleotides with TAC as the start
						int j = 0;
						while(i < dnaLength){						
							trueDNA[j] = charArr[i];				//Copy from charArr to trueDNA
							j++;
							i++;
						}
						//trueDNAStrand = new String(trueDNA);
						trueDNAStrand = String.valueOf(trueDNA);
					}
				}
			}
		}
		//System.out.println("findstart string" + trueDNAStrand);
		return trueDNAStrand;
	}

	//Input: DNA string that the user inputted
	//Output: Either their original input if it consists of only ATCG or "BADINPUT" if they input anything else
	//Purpose: Check that the user doesn't put in bad(anything that is not A/T/C/G) input
	public static String errorChecking(String input){
		input = input.toUpperCase();			//Make input all caps
		char[] errorChecking = input.toCharArray();
		//Error checking
		for(char nucleotide : errorChecking){		//Iterate thru each and every element using advanced for loop
			if(nucleotide == 'A' || nucleotide == 'T' || nucleotide == 'C' || nucleotide == 'G'){
				//Character in string is a nucleotide
				//Keep going until you reach the end.
				//End is when youre done with the for loop
			}else{
				//User inputted bad genome. Inform them
				input = "BADINPUT";
			}
		}
		return input;
	}
}
