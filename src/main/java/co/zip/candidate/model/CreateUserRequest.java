package co.zip.candidate.model;

public class CreateUserRequest {
    
    private String email;
    
    private Float monthlySalary;
    
    private Float monthlyExpenses;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Float getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Float monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
    
}
