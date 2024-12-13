import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules;// List of molecules
    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;

    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }



    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        MolecularStructure mls = new MolecularStructure(molecules);
        List<Integer> counts = mls.getCounts();
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        HashMap<Integer , List<String>> connected = mls.getConnected();
        for (int i = 0; i < counts.size(); i++) {

            List<String> str = connected.get(counts.get(i));
            MolecularStructure molecularStructure = new MolecularStructure();
            for (int j = 0; j < str.size(); j++) {
                for (int k = 0; k < molecules.size(); k++) {
                    if(molecules.get(k).getId().equals(str.get(j))){
                        molecularStructure.addMolecule(molecules.get(k));
                    }
                }
            }
            structures.add(molecularStructure);
        }





        /* YOUR CODE HERE */ 

        return structures;
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        if(species.equals("typical humans")){
            System.out.println(molecularStructures.size() + " molecular structures have been discovered in regular humans.");
        }
        else{
            System.out.println(molecularStructures.size() + " molecular structures have been discovered in Vitales individuals.");
        }
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.print("Molecules in Molecular Structure " + (i+1) + ": " + molecularStructures.get(i).toString());
            System.out.println();
        }
        
        /* YOUR CODE HERE */ 

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for (int i = 0; i < targeStructures.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < sourceStructures.size(); j++) {
                if(targeStructures.get(i).equals(sourceStructures.get(j))){
                    flag = false;
                    break;
                }
            }
            if(flag){
                anomalyList.add(new MolecularStructure(targeStructures.get(i).getMolecules()));
            }

        }
        
        /* YOUR CODE HERE */ 

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.println(molecularStructures.get(i).toString());

        }
        /* YOUR CODE HERE */ 

    }
}
