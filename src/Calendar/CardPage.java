package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalTime;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.SelectedDate;

public class CardPage {

        JFrame frame = new JFrame();
        CardPage(String username, Integer meetId, Date selectedDate,String selectedTime) {
                JFrame frame = new JFrame();
                frame.setSize(400,400);
                DateChooser dateChooser = new DateChooser();
                JPanel panel = new JPanel();
                String[] time = new String[25];
                JTextField room = new JTextField();
                room.setBorder(BorderFactory.createTitledBorder("Room"));
                JTextField guests = new JTextField();
                guests.setBorder(BorderFactory.createTitledBorder("Guests"));
                for (int i = 0;i<25;i++)
                {
                        if (i<10)
                        {
                                time[i] = "00:0"+i;
                        }
                        else
                        {
                                time[i] = "00:"+i;
                        }
                }
                JComboBox<String> startTime = new JComboBox<>(time);
                startTime.setBorder(BorderFactory.createTitledBorder("Start time"));
                startTime.setBackground(Color.white);
                JComboBox<String> endTime = new JComboBox<>(time);
                endTime.setBorder(BorderFactory.createTitledBorder("End time"));
                endTime.setBackground(Color.white);
                JButton apply = new JButton();
                JButton delete = new JButton();
                dateChooser.setSelectedDate(selectedDate);

                for(int i = 0;i<25;i++)
                {
                        if(meetId==-1)
                        {
                                if(time[i].equals(selectedTime))
                                {
                                        startTime.setSelectedIndex(i);
                                        endTime.setSelectedIndex(i+1);
                                        break;
                                }
                        }
                        else
                        {
                                if(time[i].equals(DataBase.userHM.get(username).meetingHM.get(meetId).startTime))
                                {
                                        startTime.setSelectedIndex(i);
                                }
                                if(time[i].equals(DataBase.userHM.get(username).meetingHM.get(meetId).endTime))
                                {
                                        endTime.setSelectedIndex(i);
                                        break;
                                }
                        }
                }
                delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(meetId!=-1)
                                {
                                        System.out.println(DataBase.userHM.get(username).meetingHM.get(meetId).toString());
                                        DataBase.DeleteMeeting(username,meetId);
                                }
                                CalendarPage.updateTable(0);
                                CalendarPage.updateTable(-1);
                                frame.dispose();
                        }
                });
                apply.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                                SelectedDate chooserSelDate = dateChooser.getSelectedDate();
                                Calendar cal = Calendar.getInstance();
                                cal.set(chooserSelDate.getYear(),chooserSelDate.getMonth()-1,chooserSelDate.getDay());

                                String startTString = startTime.getSelectedItem().toString();
                                String[] startTimeParts = startTString.split(":");
                                int startHour = Integer.parseInt(startTimeParts[0]);
                                int startMinute = Integer.parseInt(startTimeParts[1]);
                                System.out.println(startHour);
                                System.out.println(startMinute);

                                String endTString = endTime.getSelectedItem().toString();
                                String[] endTimeParts = endTString.split(":");
                                int endHour = Integer.parseInt(endTimeParts[0]);
                                int endMinute = Integer.parseInt(endTimeParts[1]);
                                System.out.println(endHour);
                                System.out.println(endMinute);

                                if (startMinute >= endMinute){
                                        showWarning("Wrong Time", "The completion time is longer than the start time");
                                }
                                else {
                                DataBase.CreateMeeting(username,cal.getTime(),startTime.getSelectedItem().toString(),endTime.getSelectedItem().toString(),room.getText(),guests.getText(),meetId);
                                CalendarPage.updateTable(0);//костыль
                                CalendarPage.updateTable(-1);//костыль
                                frame.dispose();
                                }
                        }
                });
                frame.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                                CalendarPage.updateTable(0);
                                CalendarPage.updateTable(-1);
                                frame.dispose();
                        }
                });
                panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));

                apply.setText("Apply");
                delete.setText("Delete");
                panel.add(dateChooser);

                JPanel timePanel = new JPanel();
                timePanel.add(Box.createRigidArea(new Dimension(5,0)));
                timePanel.add(startTime);
                timePanel.add(endTime);
                timePanel.setBackground(Color.white);
                panel.add(timePanel);

                JPanel roomGuest = new JPanel();
                roomGuest.setLayout(new BoxLayout(roomGuest,BoxLayout.Y_AXIS));
                roomGuest.add(Box.createRigidArea(new Dimension(0,5)));
                roomGuest.add(room);
                roomGuest.add(guests);
                roomGuest.setBackground(Color.white);
                panel.add(roomGuest);

                JPanel btnPanel = new JPanel();
                btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
                btnPanel.add(Box.createRigidArea(new Dimension(15,0)));
                btnPanel.add(apply);
                btnPanel.add(delete);
                btnPanel.setBackground(Color.white);
                panel.add(btnPanel);

                panel.setBackground(Color.WHITE);
                frame.setSize(300,450);
                frame.add(panel);
                frame.setVisible(true);
        }
        private void showWarning (String title, String message){
                JOptionPane.showMessageDialog(frame,message,title,JOptionPane.ERROR_MESSAGE);
        }
}
