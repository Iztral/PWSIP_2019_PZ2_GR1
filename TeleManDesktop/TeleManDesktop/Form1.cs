using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using CryptSharp;

namespace TeleManDesktop
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        string connectionString = null;
        MySqlConnection connection;

        private void Form1_Load(object sender, EventArgs e)
        {
            //connectionString = "SERVER=teleman.cba.pl;USERNAME=teleman;PASSWORD=Hasloteleman1;DATABASE=teleman;SslMode=none;Convert Zero Datetime=true";
            connectionString = "SERVER=mn14.webd.pl;USERNAME=teleman1_new;PASSWORD=haslo1234;DATABASE=teleman1_teleman;SslMode=none;Convert Zero Datetime=true";
            connection = new MySqlConnection(connectionString);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string passhash = EasyEncryption.SHA.ComputeSHA256Hash(textBox2.Text);
            //MessageBox.Show(passhash);

            string r = null;
            string p = null;

            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT Rank, Password FROM Users WHERE Login=@USERNAME", connection);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@USERNAME", textBox1.Text);

            MySqlDataReader rdr = null;

            rdr = cmd.ExecuteReader();
            if (rdr.HasRows)
            {
                while (rdr.Read())
                {
                    r = rdr[0].ToString();
                    p = rdr[1].ToString();
                    if (r != null && p != null) break;
                }
            }
            if (rdr != null) rdr.Close();

            if (p == passhash && r == "admin" || r == "root")
            {
                new Form3(connection).ShowDialog();
                textBox2.Text = "";
            }
            else if (p == passhash && r == "dispatcher")
            {
                new Form2(connection).ShowDialog();
                textBox2.Text = "";
            }
            else MessageBox.Show("Wrong credentials or no privileges");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            connection.Close();
            Close();
        }
    }
}
