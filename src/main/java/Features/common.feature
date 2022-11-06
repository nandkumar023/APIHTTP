Feature: blue yonder practice concepts for API

  @sanity @smoke
  Scenario Outline: cucumber practice for blue yonder

    Given nandkumar datatable concept
      | datatable_nandkumar | datatable_sonali |

    Given nandkumar map concepts
      |   username     |    password       |
      | map_nandkumar  |  map_babar@99     |
      | map_sonali     |  map_surwase@99   |

    Given nandkumar without "without_example_nanda" and "without_example_sona" example concept

    Given nandkumar "<username>" and "<password>" with example concepts
    Examples:
      |          username        |       password        |
      | with_example_nandkumar   | with_example_nand123  |
      | with_example_sonali      | with_example_sona123  |