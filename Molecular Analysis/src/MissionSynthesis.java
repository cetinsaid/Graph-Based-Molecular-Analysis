import java.text.DecimalFormat;
import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures;// Anomalies in Vitales structures compared to humans
    private List<Molecule> molecules = new ArrayList<>();
    private HashMap<String , List<Bond>> bonds = new HashMap<>();
    private PriorityQueue<Bond> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Bond::getWeight));
    private HashMap<String , Boolean> marked = new HashMap<>();
    private List<String> human = new ArrayList<>();
    private List<String> vitale = new ArrayList<>();

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;

        for (MolecularStructure humanStructure : humanStructures) {
            molecules.add(humanStructure.getMoleculeWithWeakestBondStrength());
            human.add(humanStructure.getMoleculeWithWeakestBondStrength().getId());
        }
        for (MolecularStructure diffStructure : diffStructures) {
            molecules.add(diffStructure.getMoleculeWithWeakestBondStrength());
            vitale.add(diffStructure.getMoleculeWithWeakestBondStrength().getId());
        }
        for (int i = 0; i < molecules.size(); i++) {
            if(!bonds.containsKey(molecules.get(i).getId())){
                bonds.put(molecules.get(i).getId() , new ArrayList<>());
            }
            for (int j = i+1; j < molecules.size(); j++) {
                if(!bonds.containsKey(molecules.get(j).getId())){
                    bonds.put(molecules.get(j).getId() , new ArrayList<>());
                }
                double a = (double) (molecules.get(i).getBondStrength() + molecules.get(j).getBondStrength()) /2;
                bonds.get(molecules.get(i).getId()).add(new Bond(molecules.get(j), molecules.get(i), a));
                bonds.get(molecules.get(j).getId()).add(new Bond(molecules.get(i) , molecules.get(j), a));
            }
        }
    }
    public void visit(String id){
        marked.put(id , true);
        for (Bond bond : bonds.get(id)){
            if(!marked.containsKey(bond.getTo().getId()) || !marked.containsKey(bond.getFrom().getId())){
                minHeap.add(bond);
            }
        }




    }
    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        visit(molecules.get(0).getId());
        while (!minHeap.isEmpty()){
            Bond bond = minHeap.poll();
            Molecule b = bond.getTo();
            Molecule a = bond.getFrom();
            if(marked.containsKey(b.getId()) && marked.containsKey(a.getId())) continue;
            serum.add(bond);
            if(!marked.containsKey(b.getId())) visit(b.getId());
            if(!marked.containsKey(a.getId())) visit(a.getId());
        }

        /* YOUR CODE HERE */ 

        return serum;
    }


    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + human.toString());
        System.out.println("Vitales molecules selected for synthesis: " + vitale.toString());
        System.out.println("Synthesizing the serum...");

        double sum = 0;
        DecimalFormat df = new DecimalFormat("#.00");

        for (int i = 0; i < serum.size(); i++) {
            if (serum.get(i).getFrom().compareTo(serum.get(i).getTo()) < 0) {
                System.out.print("Forming a bond between " + serum.get(i).getFrom() + " - " + serum.get(i).getTo() + " with strength " + df.format(serum.get(i).getWeight()));
            } else {
                System.out.print("Forming a bond between " + serum.get(i).getTo() + " - " + serum.get(i).getFrom() + " with strength " + df.format(serum.get(i).getWeight()));
            }
            System.out.println();
            sum += serum.get(i).getWeight();
        }
        System.out.println("The total serum bond strength is " + df.format(sum));


        /* YOUR CODE HERE */ 

    }
}
