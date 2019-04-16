using MySql.Data.MySqlClient;
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
using System.Windows.Shapes;

namespace Tele_ManDesktop
{
    /// <summary>
    /// Logika interakcji dla klasy AdminPanel.xaml
    /// </summary>
    public partial class AdminPanel : Window
    {
        public AdminPanel(MySqlConnection con)
        {
            InitializeComponent();
            this.con = con;
            listUsers();
        }

        MySqlConnection con;

        private void listUsers()
        {
            if (con.State == ConnectionState.Closed) con.Open();
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Users", con);
            /*DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Users");*/
            DataTable dt = new DataTable("Users");
            dataadapter.Fill(dt);
            admingrid.ItemsSource = dt.DefaultView;
            admingrid.Focus();
        }
    }
}
