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
            connectionString = "SERVER=teleman.cba.pl;USERNAME=teleman;PASSWORD=Hasloteleman1;DATABASE=teleman;SslMode=none;Convert Zero Datetime=true";
            connection = new MySqlConnection(connectionString);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string r = null;

            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT Rank FROM Users WHERE Login=@USERNAME AND Password=@PASSWORD", connection);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@USERNAME", textBox1.Text);
            cmd.Parameters.AddWithValue("@PASSWORD", textBox2.Text);

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

            if (r == "admin" || r == "root")
            {
                new Form3(connection).ShowDialog();
                textBox2.Text = "";
            }
            else if (r == "dispatcher")
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
