using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using MySql.Data.MySqlClient;

namespace Tele_ManDesktop
{
    /// <summary>
    /// Logika interakcji dla klasy MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        MySqlConnection con;
        string conString = "Server=localhost;Database=teleman;Uid=root;";
        public MainWindow()
        {
            InitializeComponent();
            connect();
        }

        private void connect()
        {
            con = new MySqlConnection(conString);
            con.Open();
        }

        private void Okbutton_Click(object sender, RoutedEventArgs e)
        {
            /*if (logintxt.Text == "admin")
                new AdminPanel().Show();
            else if (logintxt.Text == "dispatcher")
                new DispatcherPanel().Show();
            else
                MessageBox.Show("I am error");*/




            string r = null;

            if (con.State == ConnectionState.Closed) con.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT Rank FROM Users WHERE Login=@USERNAME AND Password=@PASSWORD", con);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@USERNAME", logintxt.Text);
            cmd.Parameters.AddWithValue("@PASSWORD", passwordtxt.Password.ToString());

            MySqlDataReader rdr = null;

            rdr = cmd.ExecuteReader();
            if (rdr.HasRows)
            {
                while (rdr.Read())
                {
                    r = rdr[0].ToString();
                    if (r != null) break;
                }
            }
            if (rdr != null) rdr.Close();

            if (r == "Admin")
            {
                AdminPanel adminPanel = new AdminPanel(con);
                adminPanel.ShowDialog();
            }
            else if (r == "Dispatcher")
            {
                DispatcherPanel dispatcherPanel = new DispatcherPanel();
                dispatcherPanel.ShowDialog();
            }
            else MessageBox.Show("Wrong credentials or user is not authorized");
        }

        private void Exitbutton_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
