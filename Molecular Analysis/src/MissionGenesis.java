import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called



    public void readXML(String filename)  {

        try{
            Scanner scanner = new Scanner(new File(filename));
            ArrayList<String> test = new ArrayList<String>();

            while(scanner.hasNextLine()){
                String line = scanner.nextLine().replaceAll("\\s", "");
                if(!line.equals("<MolecularData>")  && !line.equals("<?xmlversion=\"1.0\"?>") && !line.equals("</MolecularData>") && !line.isEmpty()){
                    test.add(line);
                }
            }

            String[] strings = test.toArray(new String[0]);

            List<Molecule> humanMolecules = new ArrayList<Molecule>();
            List<Molecule> vitalesMolecules = new ArrayList<Molecule>();
            if(strings[0].equals("<HumanMolecularData>")){
                int a = 0;
                for (int i = 1; !strings[i].equals("</HumanMolecularData>"); i++) {
                    readingHelper(strings, humanMolecules, i);
                    a = i;
                }
                for (int i = a+1; !strings[i].equals("</VitalesMolecularData>"); i++) {
                    readingHelper(strings , vitalesMolecules , i);
                }

            }
            else{
                int a =0;
                for (int i = 1; !strings[i].equals("</VitalesMolecularData>"); i++) {
                    readingHelper(strings, vitalesMolecules, i);
                }
                for (int i = a+1; !strings[i].equals("</HumanMolecularData>"); i++) {
                    readingHelper(strings , humanMolecules , i);
                }
            }


            molecularDataHuman = new MolecularData(humanMolecules);
            molecularDataVitales = new MolecularData(vitalesMolecules);

        }
        catch (FileNotFoundException e){
            System.out.println("file not found");

        }









        /* YOUR CODE HERE */ 
        
    }


    private void readingHelper(String[] strings, List<Molecule> Molecules, int i) {
        if(strings[i].equals("<Molecule>")){
            StringBuilder id = new StringBuilder();
            StringBuilder bondStrenght = new StringBuilder();
            List<String> bonds =new ArrayList<String>();

            for (int j = 4; strings[i+1].charAt(j) != '<'; j++) {
                id.append(strings[i + 1].charAt(j));
            }

            for (int j = 14; strings[i+2].charAt(j) != '<' ; j++) {
                bondStrenght.append(strings[i+2].charAt(j));
            }
            if(!strings[i+3].equals("<Bonds/>")){
                for (int j = i+4; !strings[j].equals("</Bonds>") ; j++) {
                    StringBuilder bond = new StringBuilder();
                    for (int k = 12; strings[j].charAt(k) != '<' ; k++) {
                        bond.append(strings[j].charAt(k));
                    }
                    bonds.add(String.valueOf(bond));
                }
            }

            Molecules.add(new Molecule(String.valueOf(id) , Integer.parseInt(String.valueOf(bondStrenght)) , bonds));
        }
    }
}
