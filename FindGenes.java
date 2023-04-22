import edu.duke.*;

public class FindGenes {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int index = dna.indexOf(stopCodon, startIndex+3);
        while (index != -1) {
            int length = index - startIndex;
            if (length%3 == 0) {
                return index;
            } else {
                index = dna.indexOf(stopCodon, index + 1);
            }
        }
        return dna.length();
    }  
    
    public String findGene(String dna) {
        String dnaUpper = dna.toUpperCase();
        int startIndex = dnaUpper.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dnaUpper, startIndex, "TAA");
        int tagIndex = findStopCodon(dnaUpper, startIndex, "TAG");
        int tgaIndex = findStopCodon(dnaUpper, startIndex, "TGA");
        int indexStopCodon = Math.min(taaIndex, Math.min(tagIndex, tgaIndex));
        if (indexStopCodon == dnaUpper.length()) {
            return "";
        } else {
            return dna.substring(startIndex, indexStopCodon+3);
        }
    }
    
    public void testFindGene() {
        String s = findGene("nonCodingDNAxxxMyGeneATGmyGenexTAAxxGeneATGTAACATGTAAATGCendTAATAAnonCodingDNAxTAGxTGA");
        System.out.println("\nFind gene: " + s);
    }
    
    public StorageResource getAllGenes(String dna) {
        StorageResource store = new StorageResource();
        String dnaUpper = dna.toUpperCase();
        int startIndex = dnaUpper.indexOf("ATG");
        while (startIndex != -1) {
            String gene = findGene(dna);
            if (gene.length() > 0) {
                store.add(gene);
                dna = dna.substring(startIndex + gene.length());
                dnaUpper = dna.toUpperCase();
            } else {
                dna = dna.substring(startIndex + 3);
                dnaUpper = dna.toUpperCase();
            }
            startIndex = dnaUpper.indexOf("ATG");
        }
        return store;
    }
    
    public void testGetAllGenes() {
        String s1 = "oneAtGMyGeneOneAATGGGGTAATGATAGAACCCGGYGGGGTAGGGCTGCCCATGendOneTAAnonCodingDnaTAGTGAZZZtaaTwoATGMyGeneTwoCATGGGGTAATGATAGCCatgCCCFalseStartTAATGATGendTwoTAGnonCodingDNATAACCCThreeATGMyGeneThreeATGGGGTAATGATAGATGccendThreeTAAnonCodingDNAccTAAfalsecccFourATGMyGeneFourATGGGGTAATGATAGCendFourTAGnonCodingdnaFiveAtgMyGeneFiveATGGGGTAATGATAGCendFiveTGAnonCodingdnaSixATGmyGeneSixATATGGGGTAATGATAGAendSixTAAnoncodingdnaSevenATGMyGeneSevenCcATGGGGTAATGATAGendSeventaAnoncodingdnaEightATGmyGeneEightATGGGGTAATGATAGGGendEighttaAnoncodingdnaCcccWrongtgaCtaaCtagCCcgNineATgmyGeneNineATGGGGTAATGATAGTaaAendNineTAAnonCodingDnaCcccTenATGmyGeneTenGATGGGGTAATGATAGCCHasFakeATGFAKEatgcendTentaanonCodingDnaCtagCtganonCodingDnaxxxElevenATGmyGeneElevenCATGGGGTAATGATAGTAAxxGeneATGTAACATGTAAATGCendElevenTAAnonCodingDnaxTAGxtgaTwelveATGmyGeneTwelveCATGGGGTAATGATAGCTheLastGeneIsATGTAGendTwelvetgaATGTAG";
        StorageResource genes = getAllGenes(s1);
        System.out.println("Find the number of gene: " + genes.size());
    }
    
    public double cgRatio(String dna) {
        int startC = 0;
        int countC = 0;
        String dnaUpper = dna.toUpperCase();
        while (true) {
            int pos = dnaUpper.indexOf("C", startC);
            if (pos == -1) {
                break;
            }
            countC++;
            startC = pos + 1;
        }
        int startG = 0;
        int countG = 0;
        while (true) {
            int pos = dnaUpper.indexOf("G", startG);
            if (pos == -1) {
                break;
            }
            countG++;
            startG = pos + 1;
        }
        int num = countC + countG;
        return ((double)num)/dna.length();
    }
    
    public int countCTG(String dna) {
        int num = 0;
        String dnaUpper = dna.toUpperCase();
        while (true) {
            int index = dnaUpper.indexOf("CTG");
            if (index == -1) {
                break;
            }
            num++;
            dnaUpper = dnaUpper.substring(index+1);
        }
        return num;
    }
    
    public void processGenes(StorageResource sr) {
        System.out.println("Strings that are longer than 60 characters:");
        int num1 = 0;
        for (String s : sr.data()) {
            int l = s.length();
            if (l > 60) {
                System.out.println(s);
                num1++;
            }
        }
        System.out.println("The number of Strings that are longer than 60 characters:");
        System.out.println(num1);
        System.out.println("Strings whose C-G-ratio is higher than 0.35:");
        int num2 = 0;
        for (String s : sr.data()) {
            double r = cgRatio(s);
            if (r > 0.35) {
                System.out.println(s);
                num2++;
            }
        }
        System.out.println("The number of strings whose C-G-ratio is higher than 0.35:");
        System.out.println(num2);
        int length = 0;
        for (String s : sr.data()) {
            if (s.length() > length) {
                length = s.length();
            }
        }
        System.out.println(length);
    }
    public void testProcessGenes() {
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        System.out.println(dna);
        StorageResource genes = getAllGenes(dna);
        System.out.println("The number of genes: " + genes.size());
        processGenes(genes);
        int num = countCTG(dna);
        System.out.println("The number of CTG: " + num);
    }
}
