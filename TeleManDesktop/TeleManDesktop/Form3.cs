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
    public partial class Form3 : Form
    {
        public Form3(MySqlConnection connection)
        {
            this.connection = connection;
            InitializeComponent();
            listcars();
        }

        MySqlConnection connection;

        private void listcars()
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Cars", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Cars");
            dataGridView1.DataSource = dataset;
            dataGridView1.DataMember = "Cars";
            dataGridView1.Focus();
        }

        private void tabControl1_Selecting(object sender, TabControlCancelEventArgs e)
        {
            if (tabControl1.SelectedTab.Text == "Cars")
            {
                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Cars", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Cars");
                dataGridView1.DataSource = dataset;
                dataGridView1.DataMember = "Cars";
                dataGridView1.Focus();
            }
            if (tabControl1.SelectedTab.Text == "Garages")
            {
                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Garage", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Garage");
                dataGridView2.DataSource = dataset;
                dataGridView2.DataMember = "Garage";
                dataGridView2.Focus();
            }
            if (tabControl1.SelectedTab.Text == "Users")
            {
                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Users", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Users");
                dataGridView3.DataSource = dataset;
                dataGridView3.DataMember = "Users";
                dataGridView3.Focus();
            }
        }

        private void toolStripMenuItem1_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow r in dataGridView1.SelectedRows)
            {
                string deletequery = "DELETE FROM Cars WHERE ID=@ID";
                MySqlCommand myCommand = new MySqlCommand(deletequery, connection);
                myCommand.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                myCommand.Connection = connection;
                myCommand.ExecuteNonQuery();
                dataGridView1.Rows.Remove(r);
                MessageBox.Show("Car deleted");
                dataGridView1.Focus();
            }
        }

        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Cars", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Cars");
            dataGridView1.DataSource = dataset;
            dataGridView1.DataMember = "Cars";
            dataGridView1.Focus();
        }

        private void deleteUserToolStripMenuItem_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow r in dataGridView2.SelectedRows)
            {
                string deletequery = "DELETE FROM Garage WHERE ID=@ID";
                MySqlCommand myCommand = new MySqlCommand(deletequery, connection);
                myCommand.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                myCommand.Connection = connection;
                myCommand.ExecuteNonQuery();
                dataGridView2.Rows.Remove(r);
                MessageBox.Show("Garage deleted");
                dataGridView2.Focus();
            }
        }

        private void refreshToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Garage", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Garage");
            dataGridView2.DataSource = dataset;
            dataGridView2.DataMember = "Garage";
            dataGridView2.Focus();
        }

        private void toolStripMenuItem3_Click(object sender, EventArgs e)
        {
            foreach (DataGridViewRow r in dataGridView3.SelectedRows)
            {
                string deletequery = "DELETE FROM UserData WHERE UserID=@ID";
                MySqlCommand myCommand = new MySqlCommand(deletequery, connection);
                myCommand.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                myCommand.Connection = connection;
                myCommand.ExecuteNonQuery();

                string deletequery2 = "DELETE FROM Users WHERE ID=@ID";
                MySqlCommand myCommand2 = new MySqlCommand(deletequery2, connection);
                myCommand2.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                dataGridView3.Rows.Remove(r);
                myCommand2.Connection = connection;
                myCommand2.ExecuteNonQuery();
                MessageBox.Show("User deleted");
                dataGridView3.Focus();
            }
        }

        private void toolStripMenuItem4_Click(object sender, EventArgs e)
        {
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Users", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Users");
            dataGridView3.DataSource = dataset;
            dataGridView3.DataMember = "Users";
            dataGridView3.Focus();
        }

        private void Form3_Load(object sender, EventArgs e)
        {
            int[] gid = new int[10];
            MySqlCommand cmd2 = new MySqlCommand("SELECT ID FROM Garage", connection);
            MySqlDataReader rdr = null;
            rdr = cmd2.ExecuteReader();
            if (rdr.HasRows)
            {
                while (rdr.Read())
                {
                    for (int i = 0; i < 1; i++)
                    {
                        gid[i] = Int32.Parse(rdr[0].ToString());
                        comboBox2.Items.Add(gid[i]);
                        comboBox2.DisplayMember = "ID";
                    }
                    //if (sid > 0) break;
                }
            }
            if (rdr != null) rdr.Close();


            int[] cid = new int[10];
            MySqlCommand cmd3 = new MySqlCommand("SELECT ID FROM Cars", connection);
            MySqlDataReader rdr2 = null;
            rdr2 = cmd3.ExecuteReader();
            if (rdr2.HasRows)
            {
                while (rdr2.Read())
                {
                    for (int i = 0; i < 1; i++)
                    {
                        cid[i] = Int32.Parse(rdr2[0].ToString());
                        comboBox3.Items.Add(cid[i]);
                        comboBox3.DisplayMember = "ID";
                    }
                    //if (sid > 0) break;
                }
            }
            if (rdr2 != null) rdr2.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox9.Text == "" || textBox10.Text == "" || textBox11.Text == "" || comboBox2.Text == "")
            {
                MessageBox.Show("Fields cannot be empty.");
            }
            else
            {
                MySqlCommand cmd = new MySqlCommand("INSERT INTO Cars(RegistrationNumber, Brand, Model, GarageID) VALUES(@RegNo, @Brand, @Model, @GID)", connection);
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@RegNo", textBox9.Text);
                cmd.Parameters.AddWithValue("@Brand", textBox10.Text);
                cmd.Parameters.AddWithValue("@Model", textBox11.Text);
                cmd.Parameters.AddWithValue("@GID", comboBox2.Text);
                cmd.ExecuteNonQuery();
                MessageBox.Show("Car added");

                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Cars", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Cars");
                dataGridView1.DataSource = dataset;
                dataGridView1.DataMember = "Cars";
                dataGridView1.Focus();

                int[] cid = new int[10];
                MySqlCommand cmd3 = new MySqlCommand("SELECT ID FROM Cars", connection);
                MySqlDataReader rdr2 = null;
                rdr2 = cmd3.ExecuteReader();
                if (rdr2.HasRows)
                {
                    while (rdr2.Read())
                    {
                        for (int i = 0; i < 1; i++)
                        {
                            cid[i] = Int32.Parse(rdr2[0].ToString());
                            comboBox3.Items.Add(cid[i]);
                            comboBox3.DisplayMember = "ID";
                        }
                        //if (sid > 0) break;
                    }
                }
                if (rdr2 != null) rdr2.Close();
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {

            if (textBox12.Text == "")
            {
                MessageBox.Show("Field cannot be empty.");
            }
            else
            {
                MySqlCommand cmd = new MySqlCommand("INSERT INTO Garage(Location) VALUES(@Location)", connection);
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@Location", textBox12.Text);
                cmd.ExecuteNonQuery();
                MessageBox.Show("Garage added");

                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Garage", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Garage");
                dataGridView2.DataSource = dataset;
                dataGridView2.DataMember = "Garage";
                dataGridView2.Focus();

                comboBox2.Items.Clear();
                int[] gid = new int[10];
                MySqlCommand cmd2 = new MySqlCommand("SELECT ID FROM Garage", connection);
                MySqlDataReader rdr = null;
                rdr = cmd2.ExecuteReader();
                if (rdr.HasRows)
                {
                    while (rdr.Read())
                    {
                        for (int i = 0; i < 1; i++)
                        {
                            gid[i] = Int32.Parse(rdr[0].ToString());
                            comboBox2.Items.Add(gid[i]);
                            comboBox2.DisplayMember = "ID";
                        }
                        //if (sid > 0) break;
                    }
                }
                if (rdr != null) rdr.Close();
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == "" || textBox2.Text == "" || textBox3.Text == "" || textBox4.Text == "" || textBox5.Text == "" || textBox6.Text == "" || textBox7.Text == "" || textBox8.Text == "" || comboBox1.Text == "" || comboBox3.Text == "")
            {
                MessageBox.Show("Fields cannot be empty.");
            }
            else
            {
                MySqlCommand cmd = new MySqlCommand("INSERT INTO Users(Login, Password, Email, Rank) VALUES(@Login, @Password, @Email, @Rank)", connection);
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@Login", textBox1.Text);
                cmd.Parameters.AddWithValue("@Password", textBox2.Text);
                cmd.Parameters.AddWithValue("@Email", textBox3.Text);
                cmd.Parameters.AddWithValue("@Rank", comboBox1.Text);
                cmd.ExecuteNonQuery();

                int userid = 0;
                MySqlCommand cmd2 = new MySqlCommand("SELECT ID FROM Users WHERE Login = @Login", connection);
                cmd2.Parameters.Clear();
                cmd2.Parameters.AddWithValue("@Login", textBox1.Text);
                MySqlDataReader rdr = null;
                rdr = cmd2.ExecuteReader();
                if (rdr.HasRows)
                {
                    while (rdr.Read())
                    {
                        userid = Int32.Parse(rdr[0].ToString());
                        if (userid > 0) break;
                    }
                }
                if (rdr != null) rdr.Close();

                MySqlCommand cmd3 = new MySqlCommand("INSERT INTO UserData(UserID, Name, Surname, Address, Telephone, City) VALUES(@UserID, @Name, @Surname, @Address, @Telephone, @City)", connection);
                cmd3.Parameters.Clear();
                cmd3.Parameters.AddWithValue("@UserID", userid);
                cmd3.Parameters.AddWithValue("@Name", textBox4.Text);
                cmd3.Parameters.AddWithValue("@Surname", textBox5.Text);
                cmd3.Parameters.AddWithValue("@Address", textBox6.Text);
                cmd3.Parameters.AddWithValue("@Telephone", textBox7.Text);
                cmd3.Parameters.AddWithValue("@City", textBox8.Text);
                cmd3.ExecuteNonQuery();
                MessageBox.Show("User created");

                MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT * FROM Users", connection);
                DataSet dataset = new DataSet();
                dataadapter.Fill(dataset, "Users");
                dataGridView3.DataSource = dataset;
                dataGridView3.DataMember = "Users";
                dataGridView3.Focus();
            }
        }
    }
}
