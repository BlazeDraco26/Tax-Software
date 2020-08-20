//Lab #10
//Jordan Cheng

// Foothill Class
public class Foothill
{
   private static int[][] brackets2020 = {
         {9525, 38700, 82500, 157000, 200000, 500000},  //single filer
         {19050, 77400, 165000, 315000, 400000, 600000},  /* married jointly or 
            qual widow(er) */
         {9525, 38700, 82000, 157000, 200000, 300000},  //married separately
         {13600, 51800, 82500, 157500, 200000, 500000}  //head of household
         };
   private static int[][] brackets2016 = {
         {9275, 37650, 91150, 190150, 413350, 415050},
         {18550, 75300, 151900, 231450, 413350, 466950},
         {9275, 37650, 75950, 115725, 206675, 233475},
         {13250, 50400, 130150, 210800, 413350, 441000}
   };
   
   public static void main(String[] args) {
      Tax tax = new Tax();
      Tax tax2 = new Tax();
      
      //mutator tests
      System.out.println("Mutator Test:");
      if (!tax.setFilingStatus(-4)) {
         System.out.println("Invalid filing status!");
      }
      if (!tax.setTaxableIncome(-5)) {
         System.out.println("Invalid taxable income!");
      }
      if (!tax.setFilingStatus(2)) {
         System.out.println("Invalid filing status!");
      } else {
         System.out.println("Valid filing status!");
      }
      if (tax.setTaxableIncome(3)) {
         System.out.println("Valid taxable income!");
      } else {
         System.out.println("Invalid taxable income!");
      }
      
      System.out.println("Savings: " + compareTax(tax, tax2, Tax.SINGLE_FILER, 
            80000));
      System.out.println("Savings: " + compareTax(tax, tax2, Tax.SINGLE_FILER, 
            150000));
      System.out.println("Savings: " + compareTax(tax, tax2, 
            Tax.MARRIED_JOINTLY_OR_QUAL_WIDOW, 450000));
      System.out.println("Savings: " + compareTax(tax, tax2, 
            Tax.MARRIED_JOINTLY_OR_QUAL_WIDOW, 140000));
      System.out.println("Savings: " + compareTax(tax, tax2, 
            Tax.HEAD_OF_HOUSEHOLD, 100000));
   }
   
   // return the savings
   public static int compareTax(Tax newRateYear, Tax oldRateYear, int fStatus, 
         int taxableIncome) {
      newRateYear.setBrackets(brackets2020);
      newRateYear.setFilingStatus(fStatus);
      newRateYear.setTaxableIncome(taxableIncome);
      System.out.println("Tax Year 2020: " + newRateYear);
      oldRateYear.setBrackets(brackets2016);
      oldRateYear.setFilingStatus(fStatus);
      oldRateYear.setTaxableIncome(taxableIncome);
      System.out.println("Tax Year 2016: " + oldRateYear);

      return newRateYear.getTax() - oldRateYear.getTax();
   }
}

// Tax class
class Tax
{

   // define constants
   private static final int DEFAULT_FILING_STATUS = 0;
   private static final double DEFAULT_TAXABLE_INCOME = 0;
   public static final int SINGLE_FILER = 0;
   public static final int MARRIED_JOINTLY_OR_QUAL_WIDOW = 1;
   public static final int MARRIED_SEPARATELY = 2;
   public static final int HEAD_OF_HOUSEHOLD = 3;
   
   // define variables
   private int filingStatus;
   private double taxableIncome;
   private double[] rates = {0.10, 0.12, 0.22, 0.24, 0.32, 0.35, 0.37};
   private int[][] brackets;
   
   // constructors
   public Tax () {
      filingStatus = DEFAULT_FILING_STATUS;
      taxableIncome = DEFAULT_TAXABLE_INCOME;
   }
   
   public Tax (int filingStatus, double taxableIncome, int[][] brackets) {
      setFilingStatus(filingStatus);
      setTaxableIncome(taxableIncome);
      this.brackets = brackets;
   }
   
   // accessors and mutators
   public boolean setFilingStatus(int filingStatus) {
      switch (filingStatus) {
      case SINGLE_FILER:
      case MARRIED_JOINTLY_OR_QUAL_WIDOW:
      case MARRIED_SEPARATELY:
      case HEAD_OF_HOUSEHOLD:
         this.filingStatus = filingStatus;
         return true;
      default:
         this.filingStatus = DEFAULT_FILING_STATUS;
         return false;
      }
   }
   
   public int getFilingStatus() {
      return filingStatus;
   }
   
   public boolean setTaxableIncome(double taxableIncome) {
      if (taxableIncome >= 0) {
         this.taxableIncome = taxableIncome;
         return true;
      } else {
         this.taxableIncome = DEFAULT_TAXABLE_INCOME;
         return false;
      }
   }
   public int[][] getBrackets()
   {
      return brackets;
   }
   public void setBrackets(int[][] brackets)
   {
      this.brackets = brackets;
   }
   
   public double getTaxableIncome()                                                                                                                                                                       {
      return taxableIncome;
   }
   
   // return calculated tax
   public int getTax() {
      double tax = 0;
      if (taxableIncome > brackets[filingStatus][5]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
               (brackets[filingStatus][1] - 
                     brackets[filingStatus][filingStatus]) * rates[1] + 
               (brackets[filingStatus][2] - 
                     brackets[filingStatus][1]) * rates[2] +
               (brackets[filingStatus][3] - 
                     brackets[filingStatus][2]) * rates[3] +
               (brackets[filingStatus][4] - 
                     brackets[filingStatus][3]) * rates[4] +
               (brackets[filingStatus][5] - 
                     brackets[filingStatus][4]) * rates[5] +
               (taxableIncome - brackets[filingStatus][5]) * rates[6];
      } else if (taxableIncome > brackets[filingStatus][4]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
               (brackets[filingStatus][1] - 
                     brackets[filingStatus][filingStatus]) * rates[1] + 
               (brackets[filingStatus][2] - 
                     brackets[filingStatus][1]) * rates[2] +
               (brackets[filingStatus][3] - 
                     brackets[filingStatus][2]) * rates[3] +
               (brackets[filingStatus][4] - 
                     brackets[filingStatus][3]) * rates[4] +
               (taxableIncome - brackets[filingStatus][4]) * rates[5];
      } else if (taxableIncome > brackets[filingStatus][3]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
               (brackets[filingStatus][1] - 
                     brackets[filingStatus][filingStatus]) * rates[1] + 
               (brackets[filingStatus][2] - 
                     brackets[filingStatus][1]) * rates[2] +
               (brackets[filingStatus][3] - 
                     brackets[filingStatus][2]) * rates[3] +
               (taxableIncome - brackets[filingStatus][3]) * rates[4];
      } else if (taxableIncome > brackets[filingStatus][2]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
               (brackets[filingStatus][1] - 
                     brackets[filingStatus][filingStatus]) * rates[1] + 
               (brackets[filingStatus][2] - 
                     brackets[filingStatus][1]) * rates[2] +
               (taxableIncome - brackets[filingStatus][2]) * rates[3];
      } else if (taxableIncome > brackets[filingStatus][1]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
               (brackets[filingStatus][1] - 
                     brackets[filingStatus][filingStatus]) * rates[1] + 
               (taxableIncome - brackets[filingStatus][1]) * rates[2];
      } else if (taxableIncome > brackets[filingStatus][0]) {
         tax = brackets[filingStatus][filingStatus] * rates[filingStatus] +
            (taxableIncome - brackets[filingStatus][filingStatus]) * rates[1];
      } else if (taxableIncome <= brackets[filingStatus][0]) {
         tax = taxableIncome * rates[filingStatus];
      }
      Double wrapperTax = new Double(tax);
      int taxIncome = wrapperTax.intValue();
      return taxIncome;
   }
   
   // return values
   public String toString() {
      return String.format("Filing Status: %d Taxable Income: %.0f Tax: %d", 
            filingStatus, taxableIncome, getTax());
   }

}

/* -------------------------------- run --------------------------------------

Mutator Test:
Invalid filing status!
Invalid taxable income!
Valid filing status!
Valid taxable income!
Tax Year 2020: Filing Status: 0 Taxable Income: 80000 Tax: 13539
Tax Year 2016: Filing Status: 0 Taxable Income: 80000 Tax: 13649
Savings: -110
Tax Year 2020: Filing Status: 0 Taxable Income: 150000 Tax: 30289
Tax Year 2016: Filing Status: 0 Taxable Income: 150000 Tax: 30226
Savings: 63
Tax Year 2020: Filing Status: 1 Taxable Income: 450000 Tax: 109260
Tax Year 2016: Filing Status: 1 Taxable Income: 450000 Tax: 116015
Savings: -6755
Tax Year 2020: Filing Status: 1 Taxable Income: 140000 Tax: 23060
Tax Year 2016: Filing Status: 1 Taxable Income: 140000 Tax: 23270
Savings: -210
Tax Year 2020: Filing Status: 3 Taxable Income: 100000 Tax: 36070
Tax Year 2016: Filing Status: 3 Taxable Income: 100000 Tax: 42256
Savings: -6186
-------------------------------------------------------------------------- */