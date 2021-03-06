package com.ali.Mtree;

/*
 * Main.java
 *
 * Created on December 6, 2001, 2:32 PM
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.*;
import java.io.*;
/**
 *
 * @author  noname
 */
public class main extends javax.swing.JFrame {

    /** Creates new form Main */
    public main() {
        initComponents();
        bootstraptextfield.setText(String.valueOf(bootreps));
        fileextenstextfield.setText(extension);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        mainpanel = new javax.swing.JPanel();
        bootstraplabel = new javax.swing.JLabel();
        bootstraptextfield = new javax.swing.JTextField();
        fileextenslabel = new javax.swing.JLabel();
        fileextenstextfield = new javax.swing.JTextField();
        gobutton = new javax.swing.JButton();
        correctionpanel = new javax.swing.JPanel();
        correctionlabel = new javax.swing.JLabel();
        nonebutton = new javax.swing.JRadioButton();
        corrmetgroup.add(nonebutton);
        poissonbutton = new javax.swing.JRadioButton();
        corrmetgroup.add(poissonbutton);
        kimurabutton = new javax.swing.JRadioButton();
        corrmetgroup.add(kimurabutton);
        tajneibutton = new javax.swing.JRadioButton();
        corrmetgroup.add(tajneibutton);
        formatpanel = new javax.swing.JPanel();
        formatlabel = new javax.swing.JLabel();
        fastabutton = new javax.swing.JRadioButton();
        formatgroup.add(fastabutton);
        phyintbutton = new javax.swing.JRadioButton();
        formatgroup.add(phyintbutton);
        physeqbutton = new javax.swing.JRadioButton();
        formatgroup.add(physeqbutton);
        clustalbutton = new javax.swing.JRadioButton();
        formatgroup.add(clustalbutton);
        indirbutton = new javax.swing.JButton();
        indirtextfield = new javax.swing.JTextField();
        outdirbutton = new javax.swing.JButton();
        outdirtextfield = new javax.swing.JTextField();
        cputextfield = new javax.swing.JTextField();
        cpulabel = new javax.swing.JLabel();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        mainpanel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints1;
        
        bootstraplabel.setText("Number of bootstrap replicates:");
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 8;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(bootstraplabel, gridBagConstraints1);
        
        bootstraptextfield.setText("100");
        bootstraptextfield.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 8;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(bootstraptextfield, gridBagConstraints1);
        
        fileextenslabel.setText("File extension:");
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(fileextenslabel, gridBagConstraints1);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(fileextenstextfield, gridBagConstraints1);
        
        gobutton.setText("Make it so");
        gobutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gobuttonActionPerformed(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 12;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(gobutton, gridBagConstraints1);
        
        correctionpanel.setLayout(new java.awt.GridLayout(5, 1));
        
        correctionpanel.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black));
        correctionlabel.setText("Distance correction method");
        correctionlabel.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black));
        correctionpanel.add(correctionlabel);
        
        nonebutton.setSelected(true);
        nonebutton.setText("No Correction");
        correctionpanel.add(nonebutton);
        
        poissonbutton.setText("Poisson");
        correctionpanel.add(poissonbutton);
        
        kimurabutton.setText("Kimura");
        correctionpanel.add(kimurabutton);
        
        tajneibutton.setText("Tajima-Nei");
        correctionpanel.add(tajneibutton);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 3;
        gridBagConstraints1.gridheight = 5;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        mainpanel.add(correctionpanel, gridBagConstraints1);
        
        formatpanel.setLayout(new java.awt.GridLayout(5, 0));
        
        formatpanel.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black));
        formatlabel.setText("Alignment Format");
        formatlabel.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black));
        formatpanel.add(formatlabel);
        
        fastabutton.setText("FASTA");
        formatpanel.add(fastabutton);
        
        phyintbutton.setText("Phylip interleaved");
        formatpanel.add(phyintbutton);
        
        physeqbutton.setText("Phylip sequential");
        formatpanel.add(physeqbutton);
        
        clustalbutton.setSelected(true);
        clustalbutton.setText("Clustal");
        formatpanel.add(clustalbutton);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 3;
        gridBagConstraints1.gridheight = 5;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        mainpanel.add(formatpanel, gridBagConstraints1);
        
        indirbutton.setText("Select input dir");
        indirbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indirbuttonActionPerformed(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(indirbutton, gridBagConstraints1);
        
        indirtextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indirtextfieldActionPerformed(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(indirtextfield, gridBagConstraints1);
        
        outdirbutton.setText("Select output dir");
        outdirbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outdirbuttonActionPerformed(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 10;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(outdirbutton, gridBagConstraints1);
        
        outdirtextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outdirtextfieldActionPerformed(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 11;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(outdirtextfield, gridBagConstraints1);
        
        cputextfield.setText("1");
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 9;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(cputextfield, gridBagConstraints1);
        
        cpulabel.setText("Number of CPU's");
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 9;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        mainpanel.add(cpulabel, gridBagConstraints1);
        
        getContentPane().add(mainpanel, java.awt.BorderLayout.CENTER);
        
        pack();
    }//GEN-END:initComponents

    private void outdirtextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outdirtextfieldActionPerformed
        outdir=new File(outdirtextfield.getText());
        didoutdir=true;
        if(outdir.isDirectory()==false){
            graph.message("directory entered is not valid");
            didoutdir=false;
        }// Add your handling code here:
    }//GEN-LAST:event_outdirtextfieldActionPerformed

    private void outdirbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outdirbuttonActionPerformed
        fc.setDialogTitle("Choose an output directory");
        fc.setFileSelectionMode(fc.FILES_AND_DIRECTORIES);
        int retval=fc.showOpenDialog(mainpanel);
        fc.setDialogTitle("");
        if(retval==fc.APPROVE_OPTION){
            File chosenfile=fc.getSelectedFile();
            if(chosenfile.isDirectory()){
                outdir=chosenfile;
                outdirtextfield.setText(outdir.getAbsolutePath());
            }
            if(chosenfile.isDirectory()==false){
                outdir=fc.getCurrentDirectory();
                outdirtextfield.setText(outdir.getAbsolutePath());
            }
            didoutdir=true;
        }// end if// Add your handling code here:
    }//GEN-LAST:event_outdirbuttonActionPerformed

    private void indirtextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indirtextfieldActionPerformed
        // Add your handling code here:
        indir=new File(indirtextfield.getText());
        didindir=true;
        if(indir.isDirectory()==false){
            graph.message("directory entered is not valid");
            didindir=false;
        }
    }//GEN-LAST:event_indirtextfieldActionPerformed

    private void gobuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gobuttonActionPerformed
        //if(inputdirselected==false){
        if(didindir==false){
            graph.error("Select input directory");
            return;
        }
        try{
            bootreps=java.lang.Integer.parseInt(bootstraptextfield.getText());
        }catch (NumberFormatException e){
            graph.error("unable to convert "+bootstraptextfield.getText()+" to int");
            return;
        }
        try{
            cpunum=java.lang.Integer.parseInt(cputextfield.getText());
        }catch (NumberFormatException e){
            graph.error("unable to convert "+cputextfield.getText()+" to int");
            return;
        }
        if(cpunum<1){
            cpunum=1;
        }
        corrmet=getcorrection();
        if(corrmet.length()==0){
            graph.error("error in assigning correction method!");
            return;
        }
        format=getformat();
        if(format.length()==0){
            graph.error("error in assigning alignment format!");
            return;
        }
        extension=fileextenstextfield.getText();
        if(didindir&&didoutdir){
            compute();
        }
        
    }//GEN-LAST:event_gobuttonActionPerformed

    private void indirbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indirbuttonActionPerformed
        fc.setDialogTitle("Choose an input directory");
        fc.setFileSelectionMode(fc.FILES_AND_DIRECTORIES);
        int retval=fc.showOpenDialog(mainpanel);
        fc.setDialogTitle("");
        if(retval==fc.APPROVE_OPTION){
            //inputdirselected=true;
            File chosenfile=fc.getSelectedFile();
            if(chosenfile.isDirectory()){
                indir=chosenfile;
                indirtextfield.setText(indir.getAbsolutePath());
            }
            if(chosenfile.isDirectory()==false){
                indir=fc.getCurrentDirectory();
                indirtextfield.setText(indir.getAbsolutePath());
            }
            didindir=true;
        }// end if
    }//GEN-LAST:event_indirbuttonActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new main().show();
    }

    javax.swing.ButtonGroup corrmetgroup=new javax.swing.ButtonGroup();
    javax.swing.ButtonGroup formatgroup=new javax.swing.ButtonGroup();
    javax.swing.JFileChooser fc=new javax.swing.JFileChooser();

    boolean didindir=false;
    boolean didoutdir=false;
    int bootreps=100;
    int cpunum=1;
    String extension=".aln";
    String outextens=".tre";
    //boolean inputdirselected=false;
    int filestodo=0;
    File indir;
    File outdir;
    PrintWriter errorlog;
    static boolean skipthis=false;
    private javax.swing.Timer timer;
    private javax.swing.ProgressMonitor progressmon;
    String corrmet;
    String format;
    maketreethread maketrees;
    java.util.Random rand=new java.util.Random(System.currentTimeMillis());
    methods mymethods=new methods();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainpanel;
    private javax.swing.JLabel bootstraplabel;
    private javax.swing.JTextField bootstraptextfield;
    private javax.swing.JLabel fileextenslabel;
    private javax.swing.JTextField fileextenstextfield;
    private javax.swing.JButton gobutton;
    private javax.swing.JPanel correctionpanel;
    private javax.swing.JLabel correctionlabel;
    private javax.swing.JRadioButton nonebutton;
    private javax.swing.JRadioButton poissonbutton;
    private javax.swing.JRadioButton kimurabutton;
    private javax.swing.JRadioButton tajneibutton;
    private javax.swing.JPanel formatpanel;
    private javax.swing.JLabel formatlabel;
    private javax.swing.JRadioButton fastabutton;
    private javax.swing.JRadioButton phyintbutton;
    private javax.swing.JRadioButton physeqbutton;
    private javax.swing.JRadioButton clustalbutton;
    private javax.swing.JButton indirbutton;
    private javax.swing.JTextField indirtextfield;
    private javax.swing.JButton outdirbutton;
    private javax.swing.JTextField outdirtextfield;
    private javax.swing.JTextField cputextfield;
    private javax.swing.JLabel cpulabel;
    // End of variables declaration//GEN-END:variables

    node maketree(File infile){
        node orgnode=new node();
        return orgnode;
    }// end maketree
    
    void compute(){
        maketrees= new maketreethread();
        int maxtreesnum=maketrees.getmaxtask();
        progressmon = new javax.swing.ProgressMonitor(main.this,"Running","", 0, maxtreesnum);
        //System.out.println(maketrees.maxtask);
        progressmon.setProgress(0);
        progressmon.setMillisToPopup(1);//popup the window in all cases
        progressmon.setMillisToDecideToPopup(10);
        TimerListener timercheck=new TimerListener();
        timer=new Timer(1000, timercheck);
        timer.start();
        maketrees.start();
    }// end compute
  
    File[] getfiles(File dir,String ending){
        File[] retarr=new File [0];
        extensionfilter tmpfilter=new extensionfilter(ending);
        if((ending.length()==0)||(ending.equals(".*"))||(ending.equals("*.*"))||(ending.equals("*"))){
            return dir.listFiles();
        }
        retarr=dir.listFiles(tmpfilter);
        return retarr;
    }// end getfilenames
    
    String getcorrection(){
        if(nonebutton.isSelected()){
            return "none";
        }
        if(poissonbutton.isSelected()){
            return "poisson";
        }
        if(kimurabutton.isSelected()){
            return "kimura";
        }
        if(tajneibutton.isSelected()){
            return "tajimanei";
        }
        return "";
    }// end getcorrection
    
    String getformat(){
        if(fastabutton.isSelected()){
            return "fasta";
        }
        if(phyintbutton.isSelected()){
            return "phyint";
        }
        if(physeqbutton.isSelected()){
            return "physeq";
        }
        if(clustalbutton.isSelected()){
            return "clustal";
        }
        return "";
    }// end getformat
    
    
    
class TimerListener implements ActionListener {

    public void actionPerformed(ActionEvent evt) {
        //System.out.println("timercall");
        if (progressmon.isCanceled()||(maketrees.getdone())) {
            progressmon.close();
            maketrees.killthread();
            timer.stop();
            //System.out.println("timer stopped");
        }else {
            progressmon.setNote(maketrees.getcurrtaskname());
            progressmon.setProgress(maketrees.getcurrtasknumber());
        }
    }
    
}//end class timerlistener
    
class extensionfilter implements FileFilter{
    String ext;
    extensionfilter(String ext){
        this.ext=ext;
    } 
    public boolean accept(java.io.File file) {
        if((file.getName().indexOf(ext))!=-1){
            return true;
        }
        return false;
    }// end accept
}// end class extensionfilter

class maketreethread extends java.lang.Thread {
    boolean killthread=false;
    boolean done=false;
    int currtasknumber=0;
    int maxtask=0;
    String currtaskname="initializing";
    File[] allfiles;
    int nocpu=1;
    
    public maketreethread(){
        //System.out.println("creating maketreethread");
        killthread=false;
        done=false;
        currtasknumber=0;
        currtaskname="initializing";
        allfiles=getfiles(indir,extension);
        filestodo=java.lang.reflect.Array.getLength(allfiles);
        maxtask=((filestodo*bootreps)+filestodo);
        this.nocpu=cpunum;
    }
    
    String getcurrtaskname(){
        return currtaskname;
    }
    
    int getmaxtask(){
        return maxtask;
    }
    
    void killthread(){
        killthread=true;
    }
    
    int getcurrtasknumber(){
        return (currtasknumber*bootreps);
    }
    
    boolean getdone(){
        return done;
    }
    
    public void run(){
        //System.out.println("in mainrun");
        if(filestodo==0){
            graph.message("no "+extension+" files found in "+indir.getName());
            return;
        }// end if
        try{
            File errorfile=new File(outdir,"errors.log");
            errorlog=dataf.out(errorfile);
        }catch (IOException e){
            graph.error("unable to open errors.log in"+outdir.getAbsolutePath());
            return;
        }
        treemakethread[] mytreemakes=new treemakethread[nocpu];
        for(int i=0;(i<nocpu)&&(i<filestodo);i++){
            //make the treemakethread objects
            mytreemakes[i]=new treemakethread(this);
        }
        System.out.println("done threadinit, "+filestodo+" files to do");
        currtasknumber=0;
        while(currtasknumber<filestodo){
            currtaskname=allfiles[currtasknumber].getName();
            //System.out.println(currtasknumber+" of "+filestodo);
            if(killthread){
                for(int i=0;(i<nocpu)&&(i<filestodo);i++){
                    mytreemakes[i].killthread=true;
                }// end for
            }// end if killthread
            int isdone=-1;
            for(int i=0;(i<nocpu)&&(i<filestodo);i++){
                if(mytreemakes[i].done){//(mytreemakes[i].done){
                    isdone=i;
                    break;
                }
            }// end for i
            if(isdone==-1){
                synchronized(this){
                    for(int i=0;(i<nocpu)&&(i<filestodo);i++){
                        if(mytreemakes[i].done){//(mytreemakes[i].done){
                            isdone=i;
                            break;
                        }
                    }// end for i
                    if(isdone==-1){
                        try{
                            //System.out.println("waiting");
                            this.wait();
                            //System.out.println("got notify");
                        }catch (InterruptedException e){
                            System.out.println("interrupted wait in main");
                            e.printStackTrace();
                        }
                    }
                }// end synchr
                continue;//restart at top of while loop
            }//end if isdone==-1
            //the rest should only exec if I have a free mytreemakes
            //System.out.println("starting "+allfiles[currtasknumber].getName()+" on "+isdone);
            mytreemakes[isdone]=new treemakethread(this,allfiles[currtasknumber],outdir,bootreps,format,corrmet,extension,outextens,errorlog);
            currtasknumber+=1;
            //mytreemakes[isdone].start();
        }// end while all files are not being done 
        boolean alldone=false;
        while (alldone==false){
            alldone=true;
            synchronized(this){
                for(int i=0;((i<nocpu)&&(i<filestodo));i++){
                    if(mytreemakes[i].done==false){
                        alldone=false;
                        break;
                    }
                }// end for i
                if(alldone==false){
                    try{
                        System.out.println("waiting at end");
                        this.wait();
                    }catch (InterruptedException e){
                        System.out.println("interrupted wait in main");
                        e.printStackTrace();
                    }
                }
            }// end synchr
        }
        errorlog.close();
        done=true;
        System.out.println("DONE");
        graph.message("DONE");
    }// end run
    
}// end maketreethread

void testnode(treenode innode,int lvl){
    System.out.println(lvl+" - "+innode.name+","+innode.length+","+innode.getboot());
    for(int i=0;i<innode.kids();i++){
        testnode(innode.kids[i],lvl+1);
    }
}// end testnode


}// end main
