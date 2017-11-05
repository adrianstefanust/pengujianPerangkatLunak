package com.mscheduler.view.meeting;

import java.util.Scanner;

public class RenegotiateMeetingViewer extends AbstractMeetingViewer {
        //singleton + constructor
        private static RenegotiateMeetingViewer instance;

        static {
            instance = new RenegotiateMeetingViewer();
        }

        public static RenegotiateMeetingViewer getInstance() {
            return instance;
        }

        public RenegotiateMeetingViewer() {
            super();
            this.init();
        }

        //end of singleton
	public String getText(int id) {
		return null;
	}

	public void handleInput(Scanner sc) {

	}

}
