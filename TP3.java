package recherche;

import java.util.Scanner;

public class TP3 {
    int NbVar;
    int NbVar2;
    int NbContraintes;
    float CoefVar[];
    float bornes[];
    float matrice[][];
    String nomVar[];
    String signes[];
    String Type;
    Scanner sc=new Scanner(System.in);
    public TP3(){
        System.out.print("Saisie le nombre des variables: ");
        NbVar=sc.nextInt();

        System.out.print("Saisie le nombre des contraintes: ");
        NbContraintes=sc.nextInt();

        System.out.print("Minimisation/Minimisation (Max/Min):");

        Type=sc.nextLine();
        Type=sc.nextLine();

        nomVar=new String[NbVar];
        signes=new String[NbContraintes];
        CoefVar=new float[NbVar];
        bornes=new float[NbContraintes];
        matrice=new float[NbContraintes][NbVar];
        for (int i=0;i<NbVar;i++){
            nomVar[i]="x"+(i+1);
        }
        NbVar2=NbVar;
    }
    public void saisieFctObjective(){
        for (int i=0;i<NbVar;i++){
            System.out.print("\nCoef Fct Objectif ["+(i+1)+"] : ");
            CoefVar[i]=sc.nextFloat();
        }
    }
    public void SaisieMatrice(){
        System.out.println("-------Matrice-----");
        for(int i=0;i<NbContraintes;i++) {
            for(int j=0;j<NbVar;j++) {
                System.out.print("Matrice["+i+"]["+j+"]: ");
                matrice[i][j]=sc.nextFloat();
            }
        }
    }
    public void SaisieContraintes(){
        signes[0]=sc.nextLine();
        for (int i=0;i<NbContraintes;i++){
            System.out.print("Contrainte "+(i+1)+" : ");
            signes[i]=sc.nextLine();
        }
    }
    public void SaisieBornes(){
        for (int i=0;i<NbContraintes;i++){
            System.out.print("Borne "+(i+1)+" : ");
            bornes[i]=sc.nextFloat();
        }
    }
    public void AffichMatrice(int x){
        for(int i=0;i<NbContraintes;i++) {
            if(matrice[i][0]!=0)
                System.out.print(matrice[i][0]+nomVar[0]);
                for(int j=1;j<x;j++) {
                if(matrice[i][j]!=0)
                    System.out.print("+"+matrice[i][j]+nomVar[j]);
            }
            System.out.print(signes[i]+bornes[i]+"\n");
        }
    }

    public boolean isCanonique(){
        boolean canonique=true;
        if(Type.equals("Max")) {
            for (int i = 0; i < (NbContraintes - NbVar); i++) {
                if (signes[i].equals(">="))
                    canonique = false;
            }
            for (int i = (NbContraintes - NbVar+1); i < NbContraintes; i++) {
                if (signes[i].equals("<="))
                    canonique = false;
            }

        }
        else{
            for (int i=0; i<(NbContraintes-NbVar);i++){
                if(signes[i].equals("<="))
                    canonique=false;
            }
            for (int i = (NbContraintes - NbVar+1); i < NbContraintes; i++) {
                if (signes[i].equals("<="))
                    canonique = false;
            }
        }
        return canonique;
    }
    public boolean isStandard(){
        boolean standard=true;
        for (int i = 0; i < (NbContraintes - NbVar); i++) {
            if (!signes[i].equals("="))
                standard = false;
        }
        for (int i = (NbContraintes - NbVar+1); i < NbContraintes; i++) {
            if (!signes[i].equals(">="))
                standard = false;
        }
        return standard;
    }

    public void Transform(){

        float M[][]=new float[NbContraintes][NbVar+1];


        for (int i=0;i<NbContraintes;i++){
            for(int j=0;j<NbVar;j++){
                M[i][j]=matrice[i][j];
            }
        }


        int nbVar2=NbContraintes;
        float coef[] = new float[nbVar2];
        for (int i=0;i<NbVar;i++){
            coef[i]=CoefVar[i];
        }
        for (int i=NbVar;i<nbVar2;i++){
            coef[i]=0;
        }
        CoefVar=coef;

        String nomVar2[]=new String[nbVar2];
        for (int i=0;i<nbVar2;i++){
            nomVar2[i]="x"+(i+1);
        }



        nomVar=nomVar2;
        String signes2[]=new String[NbContraintes];
        for(int j=0;j<NbVar;j++){
            signes2[j]=signes[j];
        }
        for (int j=NbVar;j<NbContraintes;j++){
            signes2[j]=">=";
        }
        signes=signes2;
        for(int i=0; i<(NbContraintes-NbVar);i++){
            if(signes[i].equals("<=")){
                M[i][NbVar]=1;
            }else{
                M[i][NbVar]=-1;
            }
            signes[i]="=";
        }
        matrice=M;
        NbVar=nbVar2;



    }


    public void Affiche(int x){
        System.out.println("-------------------------------------");
        System.out.println("-------------------------------------");
        System.out.println("-------------------------------------");
        System.out.print(Type+"( ");
        System.out.print(CoefVar[0]+nomVar[0]);
        for (int i=1;i<this.NbVar;i++){
            System.out.print("+"+CoefVar[i]+nomVar[i]);
        }
        System.out.print("=Z) \n");
        System.out.println("  SC:");
        AffichMatrice(x);

    }

    public static void main(String[] args) {
        TP3 tp=new TP3();
        tp.saisieFctObjective();
        tp.SaisieMatrice();
        tp.SaisieContraintes();
        tp.SaisieBornes();
        tp.Affiche(tp.NbVar);
        if(tp.isStandard()){
            System.out.println("Le Programme Linéaire est Sous Forme Standard");
        }else{
            System.out.println("Le Programme Linéaire n'est pas Sous Forme Standard");
        }
        if(tp.isCanonique()){
            System.out.println("Le Programme Linéaire est Sous Forme Canonique");
        }else{
            System.out.println("Le Programme Linéaire n'est pas Sous Forme Canonique");
        }
        tp.Transform();
        System.out.println("---------------PL SFS----------");
        tp.Affiche(tp.NbVar2+1);

    }
}
