package com.mscheduler.view.invitation;

public class InvitationDetailVewer extends AbstractInvitationViewer {
    //singleton + constructor
        private static InvitationDetailVewer instance;

        static{
            instance = new InvitationDetailVewer();
        }

        public static InvitationDetailVewer getInstance(){
          return instance;
        }
        
        private InvitationDetailVewer() {
            super();
        }
    //end of singleton
        
    @Override
    public String getText(int meeting_id) {
        //Kamus
        String detail;

        //Algoritma
        if (ic.isMeetingIdValid(meeting_id,false)) {
            detail = this.ic.detailInvitation(meeting_id);
            return detail + "\n";
        }else{
            return "Invalid meeting id\n";
        }
        
    }

    @Override
    public String getHelp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
