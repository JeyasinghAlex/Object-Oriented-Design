package Task.Transaction.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadOffice {

    private  Map<String, Branch> branches = new HashMap<>();
    private static HeadOffice headOffice = null;
    private static int amount = 0;

    private HeadOffice() {

    }

    public static HeadOffice getInstance() {
        if (headOffice == null) {
            headOffice = new HeadOffice();
        }
        return headOffice;
    }

    public void createBranch(String branchId) {
        Branch branch = new Branch();
        branch.setIfsc(branchId);
        branches.put(branchId, branch);
    }

    public Branch getBranchById(String branchId)  {
        if (!branches.containsKey(branchId)) {
            System.out.println("Branch not found ");
            return null;
        }
        return branches.get(branchId);
    }

    public List<Branch> getAllBranches() {
        List<Branch> allBranches = new ArrayList<>();
        for (String branchId : branches.keySet()) {
            allBranches.add(branches.get(branchId));
        }
        return allBranches;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        HeadOffice.amount += amount;
    }
}
