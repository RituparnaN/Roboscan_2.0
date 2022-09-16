export const sections = [
    {
        id: 1,
        name: 'Executive Summary',
        icon: 'custDetails',
        expandable: false,
        formDetails: [{}],
        type: 'form',
        hasForm: true,
        hasGraph: false,
        graph: null,
        graphList: null,
    },

    {
        id: 2,
        name: 'Real-Time Screening',
        icon: 'rtScreening',
        expandable: true,
        type: 'graph',
        hasGraph: true,
        hasForm: false,
        hasTable: false,
        graph: 'PIE',
        graphList: ['PIE'],
    },

    {
        id: 3,
        name: 'Alert Details',
        icon: 'actionItem',
        expandable: true,
        type: 'text',
        hasGraph: false,
        hasForm: false,
        hasTable: false,
        graph: null,
        graphList: null,
    },

    {
        id: 4,
        name: 'Customer Overview',
        expandable: true,
        icon: 'custDetails',
        type: 'graph',
        hasGraph: true,
        hasFrom: false,
        hasTable: false,
        graph: 'BAR',
        graphList: ['BAR'],
    },

    {
        id: 5,
        name: 'Links',
        icon: 'links',
        expandable: true,
        type: 'form',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        formData: [
            {
                name: 'From Date',
                type: 'text',
                value: '11/08/2022',
            },
            {
                name: 'To Date',
                type: 'text',
                value: '18/08/2022',
            },
            {
                name: 'Account No.',
                type: 'text',
                value: '303353508742',
            },
            {
                name: 'Level Count',
                type: 'text',
                value: '5',
            },
        ],
        graph: null,
        graphList: null,
    },

    {
        id: 6,
        name: 'Past History',
        icon: 'pastHistory',
        expandable: true,
        type: 'graph',
        hasGraph: true,
        hasFrom: false,
        hasTable: false,
        graph: 'SCATTER',
        graphList: ['SCATTER'],
    },

    {
        id: 7,
        name: 'Related Parties',
        icon: 'relatedParties',
        expandable: true,
        type: 'form',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        formData: [
            {
                name: 'Lorem Ipsum',
                type: 'text',
                value: '',
            },
            {
                name: 'Lorem Ipsum',
                type: 'text',
                value: '',
            },
            {
                name: 'Lorem Ipsum',
                type: 'text',
                value: '',
            },
            {
                name: 'Lorem Ipsum',
                type: 'text',
                value: '',
            },
        ],
        graph: null,
        graphList: null,
    },

    {
        id: 8,
        name: 'Customer Details',
        icon: 'custDetails',
        expandable: true,
        type: 'form',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        formData: [
            {
                name: 'Customer Name',
                type: 'text',
                value: 'Burhanuddin Patrawala',
            },
            {
                name: 'Customer Risk Rating',
                type: 'text',
                value: 'High',
            },
            {
                name: 'Account Risk Rating',
                type: 'text',
                value: 'Medium',
            },
            {
                name: 'Customer Type',
                type: 'text',
                value: 'VVIP',
            },
            {
                name: 'KYC last Update Date',
                type: 'check',
                options: [],
                value: '11/08/2022',
            },
            {
                name: 'Occupation',
                type: 'text',
                value: 'Minister',
            },
            {
                name: 'Nature Of Business',
                type: 'text',
                value: 'Heavy Transport',
            },
        ],
        graph: null,
        graphList: null,
    },

    {
        id: 9,
        name: 'Email Exchange',
        icon: 'email',
        expandable: true,
        type: 'form',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        formData: [
            {
                name: 'Compose',
                type: 'button',
                function: 'modal',
                modalType: 'compose',
                value: 'Compose',
            },
            {
                name: 'Inbox',
                type: 'button',
                function: 'modal',
                modalType: 'inbox',
                value: 'Inbox (12 Unread)',
            },
            {
                name: 'Sent',
                type: 'button',
                function: 'sent',
                value: 'Sent',
            },
        ],
        graph: null,
        graphList: null,
    },

    {
        id: 10,
        name: 'View Comment For Case',
        icon: 'viewComments',
        expandable: true,
        type: 'text',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        graph: null,
        graphList: null,
        sectionData: [
            'System - 121012 - 11/May/2022 08:08',
            'System - 121012 - 11/May/2022 08:08',
            'AMLUser - 121012 - 11/May/2022 08:08',
            'AMLUser - 121012 - 11/May/2022 08:08',
            'AMLUser - 121012 - 11/May/2022 08:08',
            'AMLUser - 121012 - 11/May/2022 08:08',
        ],
    },

    {
        id: 11,
        name: 'Add/View EDD',
        icon: 'viewEdd',
        expandable: true,
        type: 'form',
        hasGraph: false,
        hasFrom: true,
        hasTable: false,
        formData: [
            {
                name: 'addEdd',
                type: 'button',
                function: 'addEdd',
                value: 'Add EDD',
            },
            {
                name: 'viewEdd',
                type: 'button',
                function: 'viewEdd',
                value: 'View EDD',
            },
        ],
        graph: null,
        graphList: null,
    },

    {
        id: 12,
        name: 'View Attach Case Evidence',
        icon: 'attach',
        expandable: true,
        type: 'upload',
        hasGraph: false,
        hasFrom: false,
        hasTable: false,
        graph: null,
        graphList: null,
    },

    {
        id: 13,
        name: 'Customer Case History',
        icon: 'summaryDetails',
        expandable: true,
        type: 'table',
        hasGraph: false,
        hasFrom: false,
        hasTable: true,
        graph: null,
        graphList: null,
    },
]

export const pages = [
    {
        id: 1,
        title: 'ViewSTR',
        menu: [
            'Indian STR - ARF',
            'Indian STR - TRF',
            'SL STR',
            'UK SAR',
            'Maldives STR',
            'Nepal STR',
            'Seychelies STR',
            'Singapore STR',
            'USA SAR',
            'Kenya SAR',
            'Maurite STR',
            'Zambie STR',
        ],
    },
    {
        id: 2,
        title: 'Button 2',
        menu: ['SubButton1', 'SubButton2', 'SubButton3'],
    },
    {
        id: 3,
        title: 'Button 3',
        menu: ['SubButton1', 'SubButton2', 'SubButton3'],
    },
]

export const chartConfig = [
    {
        type: 'SCATTER',
        option: {
            title: {
                text: 'Top Performing Securities',
                subtext: 'September 2019',
            },
            xAxis: {},
            yAxis: {},
            tooltip: {
                position: 'top',
            },
            series: [
                {
                    symbolSize: 10,
                    data: [
                        [10.0, 8.04],
                        [8.07, 6.95],
                        [13.0, 7.58],
                        [9.05, 8.81],
                        [11.0, 8.33],
                        [14.0, 7.66],
                        [13.4, 6.81],
                        [10.0, 6.33],
                        [14.0, 8.96],
                        [12.5, 6.82],
                        [9.15, 7.2],
                        [11.5, 7.2],
                        [3.03, 4.23],
                        [12.2, 7.83],
                        [2.02, 4.47],
                        [1.05, 3.33],
                        [4.05, 4.96],
                        [6.03, 7.24],
                        [12.0, 6.26],
                        [12.0, 8.84],
                        [7.08, 5.82],
                        [5.02, 5.68],
                    ],
                    type: 'scatter',
                },
            ],
        },
    },
    {
        type: 'PIE',
        option: {
            tooltip: {
                trigger: 'item',
            },
            legend: {
                top: '5%',
                left: 'center',
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center',
                    },
                    //   emphasis: {
                    //       label: {
                    //           show: true,
                    //           fontSize: '40',
                    //           fontWeight: 'bold',
                    //       },
                    //   },
                    labelLine: {
                        show: false,
                    },
                    data: [
                        {
                            value: 750,
                            name: 'Search Engine',
                            itemStyle: {
                                color: '#ffc600',
                            },
                        },
                        {
                            value: 750,
                            name: 'Direct',
                            itemStyle: {
                                color: '#65d8cc',
                            },
                        },
                        {
                            value: 750,
                            name: 'Email',
                            itemStyle: {
                                color: '#313679',
                            },
                        },
                    ],
                },
            ],
        },
    },
    {
        type: 'BAR',
        option: {
            legend: {},
            tooltip: {},
            dataset: {
                source: [
                    ['product', '2015', '2016', '2017', '2018', '2019'],
                    ['Matcha Latte', 43.3, 85.8, 93.7, 81.3, 75],
                    ['Milk Tea', 83.1, 73.4, 55.1, 61, 24],
                ],
            },
            xAxis: { type: 'category' },
            yAxis: {},
            barWidth: 10,
            barGap: '90%',
            // Declare several bar series, each will be mapped
            // to a column of dataset.source by default.
            series: [
                {
                    type: 'bar',
                    itemStyle: {
                        color: '#313679',
                    },
                },
                {
                    type: 'bar',
                    itemStyle: {
                        color: '#65d8cc',
                    },
                },
                {
                    type: 'bar',
                    itemStyle: {
                        color: '#ffc600',
                    },
                },
                {
                    type: 'bar',
                    itemStyle: {
                        color: '#65d8cc',
                    },
                },
                {
                    type: 'bar',
                    itemStyle: {
                        color: '#ffc600',
                    },
                },
            ],
        },
    },
]

export const tableData = {
    HEADER: [
        'CASENO',
        'DESCRIPTION',
        'CUSTOMERID',
        'CUSTOMERNAME',
        'USERCODE',
        'AMLUSERCODE',
        'AMLOUSERCODE',
        'MLROUSERCODE',
        'USERCOMMENTS',
        'AMLUSERCOMMENTS',
        'AMLOCOMMENTS',
        'MLROCOMMENTS',
        'USERTIMESTAMP',
        'AMLUSERTIMESTAMP',
        'AMLOTIMESTAMP',
        'MLROTIMESTAMP',
    ],
    DATA: [
        [
            '119955',
            null,
            '201077902',
            'K. K. BANSAL',
            'BranchUser',
            'AMLUSER',
            null,
            null,
            null,
            null,
            null,
            null,
            '26/09/2020',
            '26/09/2020',
            null,
            null,
        ],
        [
            '119948',
            null,
            '201077902',
            'K. K. BANSAL',
            'SYSTEM',
            'AMLUser',
            null,
            null,
            null,
            null,
            null,
            null,
            '07/08/2020',
            '07/08/2020',
            null,
            null,
        ],
        [
            '119863',
            null,
            '201077902',
            'K. K. BANSAL',
            'SYSTEM',
            'AMLUser',
            null,
            null,
            null,
            null,
            null,
            null,
            '08/05/2018',
            '08/05/2018',
            null,
            null,
        ],
    ],
}
