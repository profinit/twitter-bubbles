var openTopWordsStream = function () {
    // {"topWords":
    //   {"you":8,"the":8,"que":4,"people":3,"this":3,"its":3,"bir":3,"dan":3,"about":2,"via":2,"daha":2,"perempuan":2,"natproductsshow":2,"brahmin":2,"kldaroluna":2}
    // }
    var eventSource = new EventSource("/twitter-bubbles/top-words");

    eventSource.onmessage = function (e) {
        // console.log("Processing top words: ", e.data);
        var data = JSON.parse(e.data);
        var dataframe = mapEntries(data);
        renderList(dataframe);
        renderBubbles(dataframe);
    };

    eventSource.onerror = function (e) {
        console.log("Top words event source failed.", e);
    };
};

// Bubbles based on: http://scottbw.github.io/d3js-bubble-example

function mapEntries(data) {
    var dataframe = [];

    Object.keys(data.topWords).forEach(function (word) {
        dataframe.push([word, data.topWords[word]]);
    });

    return dataframe;
}

function drawBubbleChart(root) {
    //
    // How big the chart is
    //
    var width = $(".bubbles").width() * 0.8;
    var height = width;

    //
    // Pick some colours for the categories (groups)
    //
    var color = d3.scale.category10();

    //
    // Create a bubble layout based on the tree of objects.
    // This adds properties x,y,r to each of our leaf objects
    // indicating where to draw them (x,y), and how big to draw them (r).
    // This is worked out using the "value" property of each leaf.
    //
    var bubble = d3.layout.pack().sort(null).size([width, height]).padding(1.5);

    // remove old svg element(s)
    $(".d3-bubble-diagram").remove();

    //
    // Make a SVG graphic
    //
    var svg = d3.select(".bubbles")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .attr("class", "d3-bubble-diagram");

    //
    // For each leaf, create a new "node" and place it in the correct
    // location using the transform attribute.
    //
    var node = svg.selectAll(".node")
        .data(bubble.nodes(root)
            .filter(function (d) {
                return !d.children;
            }))
        .enter()
        .append("g")
        .attr("class", "node")
        .attr("transform", function (d) {
            return "translate(" + d.x + "," + d.y + ")";
        });

    //
    // For each node, make a circle of the correct radius (r) and
    // colour it in according to the group it belongs to
    //
    node.append("circle")
        .attr("r", function (d) {
            return d.r;
        })
        .style("fill", function (d) {
            return color(d.group)
        });

    //
    // For each node, add a label to the middle of the circle
    //
    node.append("text")
        .attr("dy", ".3em")
        .style("text-anchor", "middle")
        .text(function (d) {
            return d.name;
        });
}

function renderBubbles(dataframe) {
    var root = {
        name: "Top Words",
        children: []
    };

    var group = 1;

    for (var i = 0; i < dataframe.length; i++) {
        var word = dataframe[i][0];
        var count = dataframe[i][1];
        root.children.push({
            name: word,
            value: Number(count),
            group: group++
        });
    }

    drawBubbleChart(root);
}

function renderList(dataframe) {
    var wordsAndCounts = [];

    for (var i = 0; i < dataframe.length; i++) {
        var word = dataframe[i][0];
        var count = dataframe[i][1];
        wordsAndCounts.push($("<div class='word-and-count'><div class='word'>" + word + ":&nbsp;</div><div class='count'>" + count + "&nbsp;</div></div>"));
    }

    $(".word-and-count").remove();

    $(".words .list").append(wordsAndCounts);

}

$(document).ready(function () {
    openTopWordsStream();
});
