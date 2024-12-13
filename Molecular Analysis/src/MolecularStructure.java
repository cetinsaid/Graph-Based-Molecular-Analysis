import java.util.*;
import java.util.stream.Collectors;

// Class representing a molecular structure
public class MolecularStructure  {


    // Private field
    private final List<Molecule> molecules;// List of molecules in the structure
    private HashMap<String , HashSet<String>> undirectedGraph = new HashMap<>();
    private HashMap<String , Boolean> marked = new HashMap<>();
    private HashMap<String , Integer> connected = new HashMap<>();
    private HashMap<Integer , List<String>> cn = new HashMap<>();
    private List<String> ids = new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
    int count = 0;

    public MolecularStructure(){
        molecules = new ArrayList<>();
    }
    public MolecularStructure(List<Molecule> molecules) {
        this.molecules = molecules;
        for (int i = 0; i < molecules.size(); i++) {
            ids.add(molecules.get(i).getId());
            List<String> test = molecules.get(i).getBonds();
            if(!undirectedGraph.containsKey(molecules.get(i).getId())){
                undirectedGraph.put(molecules.get(i).getId() , new HashSet<>());
            }
            for (int j = 0; j < test.size(); j++) {
                if(!undirectedGraph.containsKey(test.get(j))){
                    undirectedGraph.put(test.get(j) , new HashSet<>());
                }
                undirectedGraph.get(test.get(j)).add(molecules.get(i).getId());
                undirectedGraph.get(molecules.get(i).getId()).add(test.get(j));
            }
        }

        for (int i = 0; i < ids.size(); i++) {
            if(!marked.containsKey(ids.get(i))){
                dfs(undirectedGraph , ids.get(i));
                counts.add(count);
                count++;
            }

        }
    }

    public void dfs(HashMap<String, HashSet<String>> udG , String id){
        marked.put(id , true);
        if(!cn.containsKey(count)){
            cn.put(count,new ArrayList<>());
        }
        cn.get(count).add(id);
        for (String moleculeId : udG.get(id)){
            if(!marked.containsKey(moleculeId)){
                dfs(udG , moleculeId);
            }
        }
    }

    public List<String> getIds() {
        return ids;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public HashMap<Integer, List<String>> getConnected() {
        return cn;
    }

    public HashMap<String, HashSet<String>> getUndirectedGraph() {
        return undirectedGraph;
    }

    // Method to check if a molecule exists in the structure
    public boolean hasMolecule(String moleculeId)  {
        return molecules.stream().map(Molecule::getId).collect(Collectors.toList()).contains(moleculeId);
    }

    // Method to add a molecule to the structure
    public void addMolecule(Molecule molecule) {
        molecules.add(molecule);
    }

    // Getter for molecules in the structure
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to get the easiest-to-bond molecule in the structure
    public Molecule getMoleculeWithWeakestBondStrength() {
        return molecules.stream().min(Comparator.comparing(Molecule::getBondStrength)).orElse(null);
    }

    // Override equals method to compare structures
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MolecularStructure other = (MolecularStructure) obj;
        List<Molecule> strs = new ArrayList<>(molecules);
        Collections.sort(strs);
        Collections.sort(other.molecules);
        return strs.equals(other.molecules);
    }

    // Override toString method to provide a string representation of the structure
    @Override
    public String toString() {
        List<Molecule> sortedMolecules = new ArrayList<>(molecules);
        Collections.sort(sortedMolecules);
        return sortedMolecules.toString();
    }
}
