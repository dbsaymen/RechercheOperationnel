package recherche;

import java.util.Scanner;

public class TP2 {
    int nbligne;
    int nbcol;
    float matrice[][];
    float identité[][];
        public TP2(int nb_ligne, int nb_col) {
        this.nbligne=nb_ligne;
        this.nbcol=nb_col;
        matrice=new float[nbligne][nbcol];
        identité=new float[nbligne][nbcol];
        ConstructId();
        afficherId();
    }
    public void ConstructId(){
            for(int i=0; i<nbligne;i++)
                for (int j=0;j<nbcol;j++)
                    if(i==j)
                        identité[i][j]=1;
                    else
                        identité[i][j]=0;
    }
    public void saisie() {
        Scanner sc2=new Scanner(System.in);
        for(int i=0;i<nbligne;i++) {
            for(int j=0;j<nbcol;j++) {
                System.out.print("Matrice["+i+"]["+j+"]: ");
                matrice[i][j]=sc2.nextFloat();
            }
        }
    }
    public void afficherId() {
        System.out.println("------------Identitté-----------");
        for(int i=0;i<nbligne;i++) {
            for(int j=0;j<nbcol;j++) {
                System.out.print(identité[i][j]+" ");}
            System.out.print("\n");}
    }
    public void afficher() {
        System.out.println("------------Matrice-----------");
        for(int i=0;i<nbligne;i++) {
            for(int j=0;j<nbcol;j++) {
                System.out.print(matrice[i][j]+" ");}
            System.out.print("\n");}
    }

    public float[] rechercheMax() {
        float max=0;
        float result[]=new float[3];
        int pos_l=0;
        int pos_c=0;
        for(int i=0;i<nbligne;i++) {
            for(int j=0;j<nbcol;j++) {
                if(Math.abs(matrice[i][j])>Math.abs(max)) {
                    max=Math.abs(matrice[i][j]);
                    pos_l=i;
                    pos_c=j;
                }
                else if (Math.abs(matrice[i][j])==Math.abs(max)) {
                    pos_l=i;
                    pos_c=j;
                }
            }
        }
        System.out.println("le coefficient le plus grand en val absolue est :\n"+max+"\nen position:");
        System.out.println("ligne: "+(pos_l+1)+" et colonnee: "+(pos_c+1));
        result[0]=max;result[1]=pos_l;result[2]=pos_c;
        return result;
    }
    public void remplacer(int i_ligne,int j_ligne,float coef) {
        for(int i=0;i<nbcol;i++) {
            matrice[i_ligne][i]=(matrice[i_ligne][i]+matrice[j_ligne][i]*coef);
        }
    }
    public void remplacer_lig(int i, int j) {
        float k;
        for(int l=0;l<nbligne;l++) {
            k=matrice[i-1][l];
            matrice[i-1][l]=matrice[j-1][l];
            matrice[j-1][l]=k;
        }
    }
    public void remplacer_col(int i, int j) {
        float k;
        for (int l = 0; l < nbcol; l++) {
            k = matrice[l][i - 1];
            matrice[l][i - 1] = matrice[l][j - 1];
            matrice[l][j - 1] = k;
        }
    }
    public float[][] extraire_sous_mat(int i,int j){
        float[][] S=new float[nbligne-i][nbcol-j];
        for(int k=i;k<nbligne;k++) {
            for(int l=j;l<nbcol;l++) {
                S[k-i][l-j]=matrice[k][l];
            }
        }
        return S;
    }

    public void Gauss(){
        float p;
        float q;
        float max[]=new float[3];
        for (int i=0 ;i<nbcol;i++){
            p=matrice[i][i];
            if(p==0){
                float[][] S=new float[nbligne-i][nbcol-i];
                S=extraire_sous_mat(i,i);
                max=rechercheMax();
                remplacer_lig(i, (int) max[1]);
                remplacer_col(i,(int) max[2]);
                p=matrice[i][i];
            }
            for(int j= i+1;j<nbligne;j++){
                q=matrice[j][i];
                matrice[j][i]=0;
                for(int k= i+1; k<(nbcol-1);k++){
                    matrice[j][k]=matrice[j][k]-(matrice[i][k]*(q/p));
                }
            }
        }
    }
    public void transpose(){
        float transpose[][] = new float[nbligne][nbcol];

        for (int c = 0; c < nbligne; c++)
            for (int d = 0; d < nbcol; d++)
                transpose[d][c] = matrice[c][d];
        matrice=transpose;
    }
    public void Jordan(){
        Gauss();
        transpose();
        Gauss();
    }


    public static void main(String[] args) {
        int nbligne,nbcol;
        System.out.println("NB ligne:");
        Scanner sc=new Scanner(System.in);
        nbligne=sc.nextInt();
        System.out.println("NB colonne:");
        Scanner sc1=new Scanner(System.in);
        nbcol=sc1.nextInt();
        TP2 matr=new TP2(nbligne,nbcol);
        matr.saisie();
        System.out.println("Matrice Initiale:");
        matr.afficher();
        matr.Jordan();
        System.out.println("Resultat Gauss Jorddan:");
        matr.afficher();
    }
}
