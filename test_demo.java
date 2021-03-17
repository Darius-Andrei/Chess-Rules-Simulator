class piece{
    private int row;
    private int col;
    private String color;
    private int[] vec_x=new int[28];
    private int[] vec_y=new int[28];
    private int index=0;
    private boolean is_in_check=false;
    public void setrow_and_setcol(int row,int col) {
        this.row = row;
        this.col=col;
    }
    public void setColor(String color){
        this.color=color;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public String getColor(){
        return color;
    }
     public boolean is_valid_move(int x,int y,piece[][] mat){
         return false;
    }
    public void set_Index(){
        this.index=0;
    }
    public void set_path(){

    }
    public void update_position_please(int x,int y,piece[][] mat){

    }
    public void atack_position(piece[][] mat){

    }

    public void set_vec(int val,int val1){
        this.vec_x[this.index]=val;
        this.vec_y[this.index]=val1;
        this.index++;
    }

    public int[] getVec_x() {
        return vec_x;
    }

    public int[] getVec_y() {
        return vec_y;
    }

    public int getIndex() {
        return index;
    }

    public void setIs_in_check(boolean b) {
        this.is_in_check = b;
    }

    public boolean get_is_in_check() {
        return is_in_check;
    }
}
class pawn extends piece{
    int path;
    int ok=0;
    public boolean is_valid_move(int x,int y,piece[][] mat){
        if(((Math.abs(x-this.getRow())==1 && y==this.getCol()) || (Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==1))&& x<=7 && x>this.getRow() && this.path==0){
            ok=0;
            piece p=new empty();
            if(Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==1){
                if(mat[x][y].getClass()!=p.getClass()){
                    if(mat[x][y].getColor()!=this.getColor()){
                        return true;
                    }
                    else return false;
                }
                else return false;
            }
            else if(Math.abs(x-this.getRow())==1 && y==this.getCol()){
                if(mat[x][y].getClass()!=p.getClass()){
                    return false;
                }
                return true;
            }
        }
        else if(((Math.abs(x-this.getRow())==1 && y==this.getCol()) || (Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==1)) && this.path==1){
            ok=1;
            piece p=new empty();
            if(Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==1){
                if(mat[x][y].getClass()!=p.getClass()){
                    if(mat[x][y].getColor()!=this.getColor()){
                        return true;
                    }
                    else return false;
                }
                else return false;
            }
            else if(Math.abs(x-this.getRow())==1 && y==this.getCol()){
                if(mat[x][y].getClass()!=p.getClass()){
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public void set_path(){
        if(this.getColor()=="White"){
            this.path=0;
        }
        else if(this.getColor()=="Black"){
            this.path=1;
        }
    }

    @Override
    public void update_position_please(int x, int y, piece[][] mat) {

        if(this.is_valid_move(x,y,mat)){
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
                //System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                int ok=1;
                for(int j=0;j<8;j++){
                    for(int k=0;k<8;k++){
                        if(mat[poz_x][poz_y].is_valid_move(i,j,mat)){
                           ok=0;
                        }
                    }
                }
                if(ok==1){
                    System.out.println(this.getColor() + " won!");
                }
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }

    @Override
    public void atack_position(piece[][] mat) {
        this.set_Index();
        if(this.getColor()=="White"){
            if(this.getCol()-1>=0 && this.getRow()+1<=7){
                this.set_vec(this.getRow()+1,this.getCol()-1);
            }
            if(this.getCol()+1<=7 && this.getRow()+1<=7){
                this.set_vec(this.getRow()+1,this.getCol()+1);
            }
        }
        else if(this.getColor()=="Black"){
            if(this.getCol()-1>=0 && this.getRow()-1>=0){
                this.set_vec(this.getRow()-1,this.getCol()-1);
            }
            if(this.getCol()+1<=7 && this.getRow()-1>=0){
                this.set_vec(this.getRow()-1,this.getCol()+1);
            }
        }
    }
}
class bishop extends piece{
    public boolean is_valid_move(int x, int y,piece[][] mat) {
        if(Math.abs(x-this.getRow())==Math.abs(y-this.getCol()) && x<=7 && y<=7){
            piece p=new empty();
            if(x>this.getRow() && y>this.getCol()){
                int i=this.getRow()+1;
                int j=this.getCol()+1;
                while(i<x){
                    if(mat[i][j].getClass()!=p.getClass()){
                        return false;
                    }
                    i++;
                    j++;
                }
            }
            else if(x<this.getRow() && y<this.getCol()){
                int i=this.getRow()-1;
                int j=this.getCol()-1;
                while(i>x){
                    if(mat[i][j].getClass()!=p.getClass()){
                        return false;
                    }
                    i--;
                    j--;
                }
            }
            else if(x>this.getRow() && y<this.getCol()){
                int i=this.getRow()+1;
                int j=this.getCol()-1;
                while(i<x){
                    if(mat[i][j].getClass()!=p.getClass()){
                        return false;
                    }
                    i++;
                    j--;
                }
            }
            else if(x<this.getRow() && y>this.getCol()){
                int i=this.getRow()-1;
                int j=this.getCol()+1;
                while(i>x){
                    if(mat[i][j].getClass()!=p.getClass()){
                        return false;
                    }
                    i--;
                    j++;
                }
            }
            if(mat[x][y].getColor()==this.getColor()){
                return false;
            }
            return true;
        }
        return false;
    }
    public void update_position_please(int x, int y, piece[][] mat) {
        if(this.is_valid_move(x,y,mat)){
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
                //System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }
    public void atack_position(piece[][] mat) {
        this.set_Index();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(this.is_valid_move(i,j,mat)){
                    this.set_vec(i,j);
                }
            }
        }
        //return super.atack_position(mat);
    }

}
class horse extends piece{
     public boolean is_valid_move(int x, int y,piece[][] mat) {
           if(((Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==2) || (Math.abs(x-this.getRow())==2 && Math.abs(y-this.getCol())==1)) && x<=7 && y<=7){
               if(mat[x][y].getColor()==this.getColor()){
                   return false;
               }
               return true;
         }
           return false;
     }
    public void update_position_please(int x, int y, piece[][] mat) {
         if(this.is_valid_move(x,y,mat)){
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
               // System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }

    @Override
    public void atack_position(piece[][] mat) {
        this.set_Index();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(this.is_valid_move(i,j,mat)){
                    this.set_vec(i,j);
                }
            }
        }
        //return super.atack_position(mat);
    }
}
class tower extends piece{
    public boolean is_valid_move(int x,int y,piece[][] mat){
        piece p=new empty();
        if((x==this.getRow() || y==this.getCol()) && x<=7 &&y<=7){
            if(x==this.getRow()){
                if(y>this.getCol()){
                    for(int j=this.getCol()+1;j<y;j++){
                        if(mat[x][j].getClass()!=p.getClass()){
                            return false;
                        }
                    }

                }
                else if(y<this.getCol()){
                    for(int j=this.getCol()-1;j>y;j--){
                        if(mat[x][j].getClass()!=p.getClass()){
                            return false;
                        }
                    }
                }
                else return false;
            }
            else if(y==this.getCol()){
                if(x>this.getRow()){
                    for(int j=this.getRow()+1;j<x;j++){
                        if(mat[j][y].getClass()!=p.getClass()){
                            return false;
                        }
                    }
                }
                else if(x<this.getRow()){
                    for(int j=this.getRow()-1;j>x;j--){
                        if(mat[j][y].getClass()!=p.getClass()){
                            return false;
                        }
                    }
                }
                else return false;
            }
            if(mat[x][y].getColor()==this.getColor()){
                return false;
            }
            return true;
        }
        return false;
    }
    public void update_position_please(int x, int y, piece[][] mat) {
        if(this.is_valid_move(x,y,mat)){
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
                //System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }
    public void atack_position(piece[][] mat) {
        this.set_Index();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(this.is_valid_move(i,j,mat)){
                    this.set_vec(i,j);
                }
            }
        }
        //return super.atack_position(mat);
    }
}
class king extends piece{
    public boolean is_valid_move(int x,int y,piece[][] mat){
        if(((x==this.getRow() && Math.abs(y-this.getCol())==1) || (Math.abs(x-this.getRow())==1 && y==this.getCol() ) || (Math.abs(x-this.getRow())==1 && Math.abs(y-this.getCol())==1)) && x<=7 && y<=7 ){
            piece p=new empty();
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(mat[i][j].getColor()!=this.getColor()){
                        for(int k=0;k<mat[i][j].getIndex();k++){
                            if(mat[i][j].getVec_x()[k]==x && mat[i][j].getVec_y()[k]==y){
                                return false;
                            }
                        }
                    }
                }
            }
            if(mat[x][y].getClass()!=p.getClass()){
                if(mat[x][y].getColor()!=this.getColor()){
                    return true;
                }
                else return false;
            }
            else return true;
        }

        return false;
    }
    public void update_position_please(int x, int y, piece[][] mat) {
        if(this.is_valid_move(x,y,mat)){
            //System.out.println("da");
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
                //System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }
    public void atack_position(piece[][] mat) {
        this.set_Index();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(this.is_valid_move(i,j,mat)){
                    this.set_vec(i,j);
                }
            }
        }
        //return super.atack_position(mat);
    }
}
class queen extends piece{
    public boolean is_valid_move(int x, int y,piece[][] mat) {
        if(((x==this.getRow() || y==this.getCol()) || (Math.abs(x-this.getRow())==Math.abs(y-this.getCol())))&& x<=7 && y<=7){
            piece p=new empty();
            if(x==this.getRow() || y==this.getCol()){
                if(x==this.getRow()){
                    if(y>this.getCol()){
                        for(int j=this.getCol()+1;j<y;j++){
                            if(mat[x][j].getClass()!=p.getClass()){
                                return false;
                            }
                        }

                    }
                    else if(y<this.getCol()){
                        for(int j=this.getCol()-1;j>y;j--){
                            if(mat[x][j].getClass()!=p.getClass()){
                                return false;
                            }
                        }
                    }
                    else return false;
                }
                else if(y==this.getCol()){
                    if(x>this.getRow()){
                        for(int j=this.getRow()+1;j<x;j++){
                            if(mat[j][y].getClass()!=p.getClass()){
                                return false;
                            }
                        }
                    }
                    else if(x<this.getRow()){
                        for(int j=this.getRow()-1;j>x;j--){
                            if(mat[j][y].getClass()!=p.getClass()){
                                return false;
                            }
                        }
                    }
                    else return false;
                }
                if(mat[x][y].getColor()==this.getColor()){
                    return false;
                }
                return true;
            }
            else if(Math.abs(x-this.getRow())==Math.abs(y-this.getCol())){
                if(x>this.getRow() && y>this.getCol()){
                    int i=this.getRow()+1;
                    int j=this.getCol()+1;
                    while(i<x){
                        if(mat[i][j].getClass()!=p.getClass()){
                            return false;
                        }
                        i++;
                        j++;
                    }
                }
                else if(x<this.getRow() && y<this.getCol()){
                    int i=this.getRow()-1;
                    int j=this.getCol()-1;
                    while(i>x){
                        if(mat[i][j].getClass()!=p.getClass()){
                            return false;
                        }
                        i--;
                        j--;
                    }
                }
                else if(x>this.getRow() && y<this.getCol()){
                    int i=this.getRow()+1;
                    int j=this.getCol()-1;
                    while(i<x){
                        if(mat[i][j].getClass()!=p.getClass()){
                            return false;
                        }
                        i++;
                        j--;
                    }
                }
                else if(x<this.getRow() && y>this.getCol()){
                    int i=this.getRow()-1;
                    int j=this.getCol()+1;
                    while(i>x){
                        if(mat[i][j].getClass()!=p.getClass()){
                            return false;
                        }
                        i--;
                        j++;
                    }
                }
                if(mat[x][y].getColor()==this.getColor()){
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public void update_position_please(int x, int y, piece[][] mat) {
        if(this.is_valid_move(x,y,mat)){
            int row_aux=this.getRow();
            int col_aux=this.getCol();
            //piece p=this;
            mat[x][y]=this;
            mat[x][y].setrow_and_setcol(x,y);
            mat[row_aux][col_aux]=new empty();
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                mat[i][j].atack_position(mat);
            }
        }
        int poz_x=0;
        int poz_y=0;
        piece p=new king();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(mat[i][j].getClass()==p.getClass() && mat[i][j].getColor()!=this.getColor()){
                    //System.out.println("curbe");
                    poz_x=i;
                    poz_y=j;
                    break;
                }
            }
        }
        for(int i=0;i<this.getIndex();i++){
            if(this.getVec_x()[i]==poz_x && this.getVec_y()[i]==poz_y){
                //System.out.println("da");
                mat[poz_x][poz_y].setIs_in_check(true);
                break;
            }
            else mat[poz_x][poz_y].setIs_in_check(false);
        }
    }
    public void atack_position(piece[][] mat) {
        this.set_Index();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(this.is_valid_move(i,j,mat)){
                    this.set_vec(i,j);
                }
            }
        }
        //return super.atack_position(mat);
    }
}
class empty extends piece{

}
public class test_demo {
    public static void main(String[] args) {
        System.out.println("aici");
          piece[][] mat=new piece[8][8];
          for(int i=5;i>=2;i--){
              for(int j=0;j<8;j++){
                  mat[i][j]=new empty();
                  mat[i][j].setrow_and_setcol(i,j);
              }
          }
          for(int j=0;j<8;j++){
              mat[6][j]=new pawn();
              mat[6][j].setrow_and_setcol(6,j);
              mat[6][j].setColor("Black");
              mat[6][j].set_path();
              mat[1][j]=new pawn();
              mat[1][j].setrow_and_setcol(1,j);
              mat[1][j].setColor("White");
              mat[1][j].set_path();
          }
          mat[0][0]=new tower();
          mat[0][0].setColor("White");
          mat[0][0].setrow_and_setcol(0,0);
          mat[0][7]=new tower();
          mat[0][7].setColor("White");
          mat[0][7].setrow_and_setcol(0,7);
          mat[7][0]=new tower();
          mat[7][0].setColor("Black");
          mat[7][0].setrow_and_setcol(7,0);
          mat[7][7]=new tower();
          mat[7][7].setColor("Black");
          mat[7][7].setrow_and_setcol(7,7);
          mat[0][1]=new horse();
          mat[0][1].setColor("White");
          mat[0][1].setrow_and_setcol(0,1);
          mat[0][6]=new horse();
          mat[0][6].setColor("White");
          mat[0][6].setrow_and_setcol(0,6);
          mat[7][1]=new horse();
          mat[7][1].setColor("Black");
          mat[7][1].setrow_and_setcol(7,1);
          mat[7][6]=new horse();
          mat[7][6].setColor("Black");
          mat[7][6].setrow_and_setcol(7,6);
          mat[0][2]=new bishop();
          mat[0][2].setColor("White");
          mat[0][2].setrow_and_setcol(0,2);
          mat[0][5]=new bishop();
          mat[0][5].setColor("White");
          mat[0][5].setrow_and_setcol(0,5);
          mat[7][2]=new bishop();
          mat[7][2].setColor("Black");
          mat[7][2].setrow_and_setcol(7,2);
          mat[7][5]=new bishop();
          mat[7][5].setColor("Black");
          mat[7][5].setrow_and_setcol(7,5);
          mat[0][3]=new queen();
          mat[0][3].setColor("White");
          mat[0][3].setrow_and_setcol(0,3);
          mat[7][3]=new queen();
          mat[7][3].setColor("Black");
          mat[7][3].setrow_and_setcol(7,3);
          mat[0][4]=new king();
          mat[0][4].setColor("White");
          mat[0][4].setrow_and_setcol(0,4);
          mat[7][4]=new king();
          mat[7][4].setColor("Black");
          mat[7][4].setrow_and_setcol(7,4);
          for(int i=0;i<8;i++){
              for(int j=0;j<8;j++) {
                  mat[i][j].atack_position(mat);
              }
          }
    }
}
