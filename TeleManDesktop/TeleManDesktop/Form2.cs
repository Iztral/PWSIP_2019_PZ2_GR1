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
    public partial class Form2 : Form
    {
        private int driverid;
        public Form2(MySqlConnection connection)
        {
            this.connection = connection;
            InitializeComponent();
            populateNew();
        }

        MySqlConnection connection;

        private void deleteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            foreach (DataGridViewRow r in dataGridView1.SelectedRows)
            {
                string deletequery = "DELETE FROM Orders WHERE ID=@ID";
                MySqlCommand myCommand = new MySqlCommand(deletequery, connection);
                myCommand.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                dataGridView1.Rows.Remove(r);
                MessageBox.Show("Order deleted.");
                dataGridView1.Focus();
                myCommand.Connection = connection;
                //connection.Open();
                myCommand.ExecuteNonQuery();
                myCommand.Connection.Close();
            }
        }

        private void tabControl1_Selecting(object sender, TabControlCancelEventArgs e)
        {
            if (tabControl1.SelectedTab.Text == "New")
            {
                populateNew();
            }
            if (tabControl1.SelectedTab.Text == "In progress")
            {
                populateOngoing();
            }
            if (tabControl1.SelectedTab.Text == "History")
            {
                populateHistory();
            }
        }

        private void assignToToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            Form4 f4 = new Form4(connection);
            f4.ShowDialog();
            this.driverid = f4.driverid;

            if (connection.State == ConnectionState.Closed) connection.Open();
            foreach (DataGridViewRow r in dataGridView1.SelectedRows)
            {
                MySqlCommand cmd = new MySqlCommand("INSERT INTO Association(UserID, OrderID) VALUES(@UserID, @OrderID)", connection);
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@UserID", driverid);
                cmd.Parameters.AddWithValue("@OrderID", r.Cells[0].Value);
                cmd.ExecuteNonQuery();
                cmd = new MySqlCommand("UPDATE Orders SET ModificationDate=@ModificationDate WHERE ID=@ID", connection);
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@ModificationDate", DateTime.Now);
                cmd.Parameters.AddWithValue("@ID", r.Cells[0].Value);
                cmd.ExecuteNonQuery();

                MessageBox.Show("Order assigned");
                populateNew();
            }
        }

        private void refreshToolStripMenuItem_Click(object sender, EventArgs e)
        {
            populateNew();
        }

        private void refreshToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            populateOngoing();
        }

        private void refreshToolStripMenuItem3_Click(object sender, EventArgs e)
        {
            populateHistory();
        }

        private void populateNew()
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT ID, Description, CreateDate, Location, Destination FROM Orders WHERE State = 'waiting' AND ModificationDate IS NULL", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Orders");
            dataGridView1.DataSource = dataset;
            dataGridView1.DataMember = "Orders";
            dataGridView1.Focus();
        }

        private void populateOngoing()
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT Orders.ID, Association.UserID, State, Description, CreateDate, ModificationDate, Location, Destination FROM `Orders`, `Association` WHERE Orders.ID=OrderID AND State != 'success' AND State != 'failed'", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Orders");
            dataGridView2.DataSource = dataset;
            dataGridView2.DataMember = "Orders";
            dataGridView2.Focus();
        }

        private void populateHistory()
        {
            if (connection.State == ConnectionState.Closed) connection.Open();
            MySqlDataAdapter dataadapter = new MySqlDataAdapter("SELECT ID, Description, Location, Destination, CreateDate, FinishDate FROM Orders WHERE State = 'success' OR State = 'failed'", connection);
            DataSet dataset = new DataSet();
            dataadapter.Fill(dataset, "Orders");
            dataGridView4.DataSource = dataset;
            dataGridView4.DataMember = "Orders";
            dataGridView4.Focus();
        }
    }
}