using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TeleManDesktop
{
    public partial class Form4 : Form
    {
        int did;
        public Form4(MySqlConnection connection)
        {
            InitializeComponent();
            this.connection = connection;
        }

        MySqlConnection connection;

        public int driverid
        {
            get { return did; }
        }

        private void Form4_Load(object sender, EventArgs e)
        {
            int[] sid = new int[10];
            MySqlCommand cmd2 = new MySqlCommand("SELECT ID FROM Users WHERE `Rank` = 'driver'", connection);
            MySqlDataReader rdr = null;
            rdr = cmd2.ExecuteReader();
            if (rdr.HasRows)
            {
                while (rdr.Read())
                {
                    for (int i = 0; i < 1; i++)
                    {
                        sid[i] = Int32.Parse(rdr[0].ToString());
                        comboBox1.Items.Add(sid[i]);
                        comboBox1.DisplayMember = "ID";
                    }
                    //if (sid > 0) break;
                }
            }
            if (rdr != null) rdr.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            did = Int32.Parse(comboBox1.SelectedItem.ToString());
            Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
