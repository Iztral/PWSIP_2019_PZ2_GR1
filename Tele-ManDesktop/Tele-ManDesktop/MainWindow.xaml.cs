using System;
using System.Collections.Generic;
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

namespace Tele_ManDesktop
{
    /// <summary>
    /// Logika interakcji dla klasy MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Okbutton_Click(object sender, RoutedEventArgs e)
        {
            if (logintxt.Text == "admin")
                new AdminPanel().Show();
            else if (logintxt.Text == "dispatcher")
                new DispatcherPanel().Show();
            else
                MessageBox.Show("I am error");
        }

        private void Exitbutton_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
